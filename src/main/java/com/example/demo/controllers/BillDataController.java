package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BillDataController {

    private final BillDataRepository billDataRepository;
    private final LogRepository logRepository;
    private final UserStatsRepository userStatsRepository;
    private final UserController userController;
    private final ClusterController clusterController;
    private final ClusterDetailRepository clusterDetailRepository;

    @Autowired
    public BillDataController(BillDataRepository billDataRepository, LogRepository logRepository, UserStatsRepository userStatsRepository, UserController userController, ClusterController clusterController, ClusterDetailRepository clusterDetailRepository) {
        this.billDataRepository = billDataRepository;
        this.logRepository = logRepository;
        this.userStatsRepository = userStatsRepository;
        this.userController = userController;
        this.clusterController = clusterController;
        this.clusterDetailRepository = clusterDetailRepository;
    }

    @CrossOrigin
    @GetMapping ("/log")
    public List<Log> getAllLogData(){
        return logRepository.findAll();
    }

    @CrossOrigin
    @GetMapping ("/bill_data")
    public List<BillData> getAllBillData(){
        return billDataRepository.findAll();
    }

    @CrossOrigin
    @GetMapping ("/bill_data/user/{id}")
    public List<BillData> getBillDataByUserId(@PathVariable("id") final int id){
        return billDataRepository.getBillDataByUserId(id);
    }

    @CrossOrigin
    @GetMapping ("/bill_data/{id}")
    public Optional<BillData> getBillDataById(@PathVariable ("id") final int id){
        return billDataRepository.findById(id);
    }

    @CrossOrigin
    @GetMapping ("/bill_data/mean_month/user/{id}")
    public Map<String, List> getCategoryAndMeanMonthByUserId(@PathVariable ("id") final int id){
        Map<String, List> mapOfLists = new HashMap<>();
        List<UserStats> userStatsList = userStatsRepository.getUserStatsByUserIdAndBillType(id, "Seasonal");
        List<String> seasonalCategoryList = new ArrayList<>();
        List<List<Double>> listOfMeanMonthLists = new ArrayList<>();

        for (UserStats userStats : userStatsList){
            List<Double> meanMonthList = new ArrayList<>(Collections.nCopies(12, 0.0));
            List<Integer> frequencyList = new ArrayList<>(Collections.nCopies(12, 0));
            List<BillData> billDataList = billDataRepository.getTrueBillDataWithDate(id,
                    userStats.getCategory(), userStats.getBiller());
            if (billDataList.isEmpty()){ continue; }

            List<BillData> processedBillDataList = calculatePercentiles(billDataList);
            for (BillData billData : processedBillDataList){
                int index = billData.getMonth() - 1;
                meanMonthList.set(index, meanMonthList.get(index) + billData.getMonthlyAmount());
                frequencyList.set(index, frequencyList.get(index) + 1);
            }

            for (int i = 0; i < meanMonthList.size(); i++) {
                if (frequencyList.get(i) > 1) {
                    meanMonthList.set(i, meanMonthList.get(i) / frequencyList.get(i));
                }
            }
            seasonalCategoryList.add(userStats.getCategory() + " " + userStats.getBiller());
            listOfMeanMonthLists.add(meanMonthList);
        }

        mapOfLists.put("seasonalCategory", seasonalCategoryList);
        mapOfLists.put("meanMonthList", listOfMeanMonthLists);
        System.out.println(seasonalCategoryList);
        System.out.println(listOfMeanMonthLists);
        return mapOfLists;
    }

    public List<BillData> calculatePercentiles(List<BillData> billDataList){
        DescriptiveStatistics stats = new DescriptiveStatistics();
        billDataList.sort(Comparator.comparing(BillData::getMonthlyAmount));
        for (BillData billData : billDataList){
            stats.addValue(billData.getMonthlyAmount());
        }
        double q1 = stats.getPercentile(25);
        double q3 = stats.getPercentile(75);
        double iqr = q3 - q1;

        billDataList.removeIf(billData -> billData.getMonthlyAmount() < q1 - 1.5 * iqr || billData.getMonthlyAmount() > q3 + 1.5 * iqr);
        return billDataList;
    }

    public List<Double> calculateMeanAndStandardDeviation(BillData billData){
        DescriptiveStatistics stats = new DescriptiveStatistics();
        List<Double> statsList = new ArrayList<>();
        List<BillData> billDataList = billDataRepository.getTrueBillDataByUserIdAndCategoryAndBiller(
                billData.getUser().getId(), billData.getCategory(), billData.getBiller());

        if (!billDataList.isEmpty()) {
            List<BillData> processedBillDataList = calculatePercentiles(billDataList);
            for (BillData eachBillData : processedBillDataList) {
                stats.addValue(eachBillData.getMonthlyAmount());
            }
            double mean = stats.getMean();
            double standardDeviation = stats.getStandardDeviation();
            statsList.add(mean);
            statsList.add(standardDeviation);
        }

        else {
            statsList.add(0.0);
            statsList.add(0.0);
        }
        return statsList;
    }

    public void updateUserStats(BillData billData){
        User user = billData.getUser();
        List<UserStats> userStatsList = userStatsRepository.getUserStatsByUserIdAndCategoryAndBiller(
                                        user.getId(), billData.getCategory(), billData.getBiller());
        if (userStatsList.isEmpty()){
            UserStats newUserStats = new UserStats();
            newUserStats.setUser(user);
            newUserStats.setNumberOfBills(1);
            newUserStats.setCategory(billData.getCategory());
            newUserStats.setBiller(billData.getBiller());
            newUserStats.setMean(billData.getMonthlyAmount());
            newUserStats.setStandardDeviation(0);
            userStatsRepository.save(newUserStats);
        }

        else {
            UserStats userStats = userStatsList.get(0);
            List<BillData> billDataList = billDataRepository.getBillDataByUserIdAndCategoryAndBiller(
                    billData.getUser().getId(), billData.getCategory(), billData.getBiller());
            if (billDataList.isEmpty()){
                userStatsRepository.deleteById(userStatsList.get(0).getId());
                return;
            }
            userStats.setNumberOfBills(billDataList.size());
            List<Double> statsList = calculateMeanAndStandardDeviation(billData);
            double mean = Math.abs(statsList.get(0));
            double standardDeviation = statsList.get(1);
            userStats.setMean(mean);
            userStats.setStandardDeviation(standardDeviation);

            double result = standardDeviation / mean;
            if (result > 0.15){
                userStats.setBillType("Seasonal");
            }
            else {
                userStats.setBillType("Nonseasonal");
            }
            userStatsRepository.save(userStats);
        }
    }

    public void calculatePeriodAndMonthlyAmount(BillData billData){
        List<BillData> billDataList = billDataRepository.getBillDataWithDate(billData.getUser().getId(),
                                    billData.getCategory(), billData.getBiller());
        if (!billDataList.isEmpty()){
            BillData existingBillData = billDataList.get(billDataList.size() - 1);
            int month = existingBillData.getMonth();
            int year = existingBillData.getYear();
            int period = billData.getMonth() + ((billData.getYear() - year) * 12) - month;
            billData.setPeriod(period);
            billData.setMonthlyAmount(Math.abs(billData.getAmount() / period));
            if (billDataList.size() == 1) {
                existingBillData.setPeriod(period);
                existingBillData.setMonthlyAmount(Math.abs(existingBillData.getAmount() / period));
                edit(existingBillData, existingBillData.getId());
            }
        }
    }

    @CrossOrigin
    @GetMapping("/bill_data/clustered_id/{id}")
    public List<Integer> getListOfClusteredBillDataId(@PathVariable ("id") final int id){
        BillData billData;
        if (getBillDataById(id).isPresent()){
            billData = getBillDataById(id).get();
        }
        else {return null;}
        List<BillData> billDataList = getListOfClusteredBillData(billData.getId());
        List<Integer> idList = new ArrayList<>();
        for (BillData eachBillData : billDataList){
            idList.add(eachBillData.getId());
        }
        return idList;
    }

    @CrossOrigin
    @GetMapping("/bill_data/clustered_data/{id}")
    public List<BillData> getListOfClusteredBillData(@PathVariable ("id") final int id){
        BillData billData;
        if (getBillDataById(id).isPresent()){
            billData = getBillDataById(id).get();
        }
        else {return null;}
       List<BillData> billDataList = billDataRepository.getTrueBillDataByUserIdAndCategoryAndBiller(
               billData.getUser().getId(), billData.getCategory(), billData.getBiller());
        List<BillData> subList;

        if (billData.isStatus() && billDataList.size() >= 3) {
            int index = billDataList.indexOf(billData);
            if (index < 2){
                return null;
            }
            else if (index < 4) {
                subList = billDataList.subList(0, index + 1);
                return predict(subList, subList.size(), billData);
            }
            else {
                subList = billDataList.subList(index - 4, index + 1);
                return predict(subList, 5, billData);
            }
        }
        return null;
    }

    @CrossOrigin
    @PostMapping ("/bill_data")
    public void add(@RequestBody BillData billData){
        User user;
        if (userController.getUserById(billData.getUser().getId()).isPresent()){
            user = userController.getUserById(billData.getUser().getId()).get();
        }
        else {return;}
        user.setTotalBill(user.getTotalBill() + 1);
        userController.edit(user, user.getId());
        billData.setUser(user);

        if (billData.getBiller().equals("")){
            billData.setBiller("p");
        }
        billData.setPeriod(1);
        billData.setMonthlyAmount(Math.abs(billData.getAmount()));

        List<BillData> billDataList = billDataRepository.getTrueBillDataByUserIdAndCategoryAndBiller(
                                    user.getId(), billData.getCategory(), billData.getBiller());
        List<UserStats> userStatsList = userStatsRepository.getUserStatsByUserIdAndCategoryAndBiller(
                billData.getUser().getId(), billData.getCategory(), billData.getBiller());

//        if (billData.getMonth() == 0 || billData.getYear() == 0){
//            billData.setPeriod(1);
//            billData.setMonthlyAmount(Math.abs(billData.getAmount()));
//        }
//        else { calculatePeriodAndMonthlyAmount(billData); }

        if (userStatsList.isEmpty() || userStatsList.get(0).getNumberOfBills() < 2){
            billData.setStatus(true);
            billData.setPredictedAmount(0);
        }
        else if (userStatsList.get(0).getBillType().equalsIgnoreCase("nonseasonal")) {
            billDataList.add(billData);
            addNonSeasonal(billData, billDataList, userStatsList);
        }

        else if (userStatsList.get(0).getNumberOfBills() < 5){
            billData.setStatus(true);
            billData.setPredictedAmount(4);
        }
        else {
            addSeasonal(billData, userStatsList);
        }

        billDataRepository.save(billData);
        updateUserStats(billData);

        //Log
        String description = "New bill " + billData.getId() + " is added";
        Log activityLog = new Log("bg-primary", DateTime.now().toString(), billData.getUser().getId(), description);
        logRepository.save(activityLog);
    }

    public void addNonSeasonal(BillData billData, List<BillData> billDataList, List<UserStats> userStatsList){
        if (billDataList.size() < 5){
            List<BillData> subList = billDataList.subList(0, billDataList.size());
            predict(subList, billDataList.size(), billData);
        }
        else {
            List<BillData> subList = billDataList.subList(billDataList.size() - 5, billDataList.size());
            predict(subList, 5, billData);
        }

        double a = Math.abs(billData.getMonthlyAmount() - billData.getPredictedAmount());
        double b = userStatsList.get(0).getStandardDeviation();
        billData.setStatus(a <= b);
    }

    public void addSeasonal(BillData billData, List<UserStats> userStatsList) {
        Cluster cluster = billData.getUser().getCluster();
        if (clusterController.clusters().isEmpty()) {
            predictSeasonal(billData, userStatsList);
        }
        else if (cluster != null){
            predictSeasonalWithCluster(billData, cluster, userStatsList);
        }
        else {
            predictSeasonalWithoutCluster(billData, userStatsList);
        }
    }

    public void predictSeasonal(BillData billData, List<UserStats> userStatsList){
        User user = billData.getUser();
        double a;
        double b;
        String categoryAndBiller = billData.getCategory() + " " + billData.getBiller();
        Map<String, List> categoryAndMeanMonthMap = getCategoryAndMeanMonthByUserId(user.getId());
        List<String> seasonalCategoryList = categoryAndMeanMonthMap.get("seasonalCategory");
        List<List<Double>> listOfMeanMonthLists = categoryAndMeanMonthMap.get("meanMonthList");
        int index = seasonalCategoryList.indexOf(categoryAndBiller);
        List<Double> chosenMeanMonthList = listOfMeanMonthLists.get(index);
        double value = chosenMeanMonthList.get(billData.getMonth() - 1);
        double userMean = userStatsList.get(0).getMean();
        double userStandardDeviation = userStatsList.get(0).getStandardDeviation();

        if (value != 0) {
            a = Math.abs(billData.getMonthlyAmount() - value);
            billData.setPeriod(2);
        } else {
            a = Math.abs(billData.getMonthlyAmount() - userMean);
            billData.setPeriod(3);
        }
        b = userStandardDeviation * 2;
        billData.setPredictedAmount(b);
        billData.setStatus(a < b);
    }

    public void predictSeasonalWithCluster(BillData billData, Cluster cluster, List<UserStats> userStatsList){
        User user = billData.getUser();
        List<ClusterDetail> clusterDetailList = clusterDetailRepository.getClusterDetailsByClusterIdAndCategoryAndBiller(
                cluster.getId(), billData.getCategory(), billData.getBiller());
        List<BillData> billDataList = billDataRepository.getTrueBillDataByUserIdAndCategoryAndBiller(
                user.getId(), billData.getCategory(), billData.getBiller());

        System.out.println(clusterDetailList);
        double a;
        double b;
        if (billDataList.size() < 5 && !clusterDetailList.isEmpty()) {
            a = Math.abs(billData.getMonthlyAmount() - clusterDetailList.get(0).getMean());
            b = clusterDetailList.get(0).getStandardDeviation() * 2;
            billData.setPeriod(1);
            billData.setPredictedAmount(b);
            billData.setStatus(a < b);
        }
        else {
            predictSeasonal(billData, userStatsList);
        }
    }

    public void predictSeasonalWithoutCluster(BillData billData, List<UserStats> userStatsList){
        double mean = userStatsList.get(0).getMean();
        double standardDeviation = userStatsList.get(0).getStandardDeviation();
        List<Cluster> clusterList = clusterController.clusters();
        List<Double> distanceList = new ArrayList<>();
        for (Cluster cluster : clusterList){
            double distance = Double.MAX_VALUE;
            List<ClusterDetail> clusterDetailList = clusterDetailRepository.getClusterDetailsByClusterIdAndCategoryAndBiller(
                    cluster.getId(), billData.getCategory(), billData.getBiller());
            if (!clusterDetailList.isEmpty()){
                distance = Math.pow(mean - clusterDetailList.get(0).getMean(), 2) +
                        Math.pow(standardDeviation - clusterDetailList.get(0).getStandardDeviation(), 2);
            }
            distanceList.add(distance);
        }

        int index = distanceList.indexOf(Collections.min(distanceList));
        Cluster chosenCluster = clusterList.get(index);
        List<ClusterDetail> chosenClusterDetailList = clusterDetailRepository.getClusterDetailsByClusterIdAndCategoryAndBiller(
                chosenCluster.getId(), billData.getCategory(), billData.getBiller());
        double a = Math.abs(billData.getMonthlyAmount() - chosenClusterDetailList.get(0).getMean());
        double b = chosenClusterDetailList.get(0).getStandardDeviation() * 2;
        billData.setStatus(a < b);
        billData.setPredictedAmount(chosenCluster.getId());
    }

    @CrossOrigin
    @PutMapping ("/bill_data/{id}")
    public void edit(@RequestBody BillData billData, @PathVariable("id") final int id){
        BillData existingBillData;
        User user;
        if (getBillDataById(id).isPresent() && userController.getUserById(billData.getUser().getId()).isPresent()){
            existingBillData = getBillDataById(id).get();
            user = userController.getUserById(billData.getUser().getId()).get();
        }
        else {return;}
        BillData proxy = new BillData();
        proxy.setUser(existingBillData.getUser());
        proxy.setCategory(existingBillData.getCategory());
        proxy.setBiller(existingBillData.getBiller());

        existingBillData.setId(id);
        existingBillData.setUser(user);
        existingBillData.setState(billData.getState());
        existingBillData.setMonth(billData.getMonth());
        existingBillData.setYear(billData.getYear());

        existingBillData.setCategory(billData.getCategory());
        existingBillData.setBiller(billData.getBiller());
        existingBillData.setAmount(billData.getAmount());
        existingBillData.setPeriod(billData.getPeriod());
        existingBillData.setMonthlyAmount(billData.getMonthlyAmount());
        existingBillData.setPredictedAmount(billData.getPredictedAmount());
        billDataRepository.save(existingBillData);

        if (proxy.getUser().getId() != existingBillData.getUser().getId() ||
        !proxy.getCategory().equalsIgnoreCase(existingBillData.getCategory()) ||
        !proxy.getBiller().equalsIgnoreCase(existingBillData.getBiller())){
            updateUserStats(proxy);
        }
        updateUserStats(existingBillData);

        //Log
        String description = "Bill " + id + " is edited";
        Log activityLog = new Log("bg-warning", DateTime.now().toString(), proxy.getUser().getId(), description);
        logRepository.save(activityLog);
    }

    @CrossOrigin
    @DeleteMapping ("/bill_data/{id}")
    public void delete(@PathVariable ("id") final int id){
        BillData existingBillData;
        if (getBillDataById(id).isPresent()){
            existingBillData = getBillDataById(id).get();
        }
        else {return;}
        User user = userController.getUserById(existingBillData.getUser().getId()).get();
        BillData proxy = new BillData();
        proxy.setUser(user);
        proxy.setCategory(existingBillData.getCategory());
        proxy.setBiller(existingBillData.getBiller());
        billDataRepository.deleteById(id);
        updateUserStats(proxy);

        //Log
        String description = "Bill " + id + " is deleted";
        Log activityLog = new Log("bg-danger", DateTime.now().toString(), proxy.getUser().getId(), description);
        logRepository.save(activityLog);
    }

    @CrossOrigin
    @GetMapping ("bill_data/confirm/{id}")
    public void confirmBillData (@PathVariable ("id") final int id){
        BillData billData;
        if (getBillDataById(id).isPresent()){
            billData = getBillDataById(id).get();
        }
        else {return;}
        billData.setStatus(!billData.isStatus());
        billDataRepository.save(billData);
        updateUserStats(billData);

        List<UserStats> userStatsList = userStatsRepository.getUserStatsByUserIdAndCategoryAndBiller(
                billData.getUser().getId(), billData.getCategory(), billData.getBiller());
        if (userStatsList.get(0).getBillType() != null){
            if (userStatsList.get(0).getBillType().equalsIgnoreCase("nonseasonal")){
                modifyFollowingNonseasonalData(billData);
            }
            else {
                modifyFollowingSeasonalData(billData);
            }
        }

        if (billData.isStatus()) {
            //Log
            String description = "Bill " + id + " is confirmed";
            Log activityLog = new Log("bg-success", DateTime.now().toString(), billData.getUser().getId(), description);
            logRepository.save(activityLog);
        }
        else {
            //Log
            String description = "Bill " + id + " is reported";
            Log activityLog = new Log("bg-danger", DateTime.now().toString(), billData.getUser().getId(), description);
            logRepository.save(activityLog);
        }
    }

    public void modifyFollowingNonseasonalData(BillData billData){
        List<BillData> billDataList = billDataRepository.getBillDataByUserIdAndCategoryAndBiller(
                billData.getUser().getId(), billData.getCategory(), billData.getBiller());
        int index = billDataList.indexOf(billData);
        List<BillData> followingDataSubList = billDataList.subList(index + 1, billDataList.size());

        for (BillData eachBillData : followingDataSubList){
            List<UserStats> userStatsList = userStatsRepository.getUserStatsByUserIdAndCategoryAndBiller(
                    billData.getUser().getId(), billData.getCategory(), billData.getBiller());
            if (eachBillData.isStatus()){
                List<BillData> dataList = billDataRepository.getTrueBillDataByUserIdAndCategoryAndBiller(
                        billData.getUser().getId(), billData.getCategory(), billData.getBiller());
                int dataIndex = dataList.indexOf(eachBillData);

                List<BillData> subDataList;
                if (dataIndex < 2){
                    return;
                }
                else if (dataIndex < 4){
                    subDataList = dataList.subList(0, dataIndex + 1);
                }
                else {
                    subDataList = dataList.subList(dataIndex - 4, dataIndex + 1);
                }
                addNonSeasonal(eachBillData, subDataList, userStatsList);
            }

            else {
                List<BillData> previousDataSubList = billDataList.subList(0, index);
                List<BillData> dataList = billDataRepository.getTrueBillDataByUserIdAndCategoryAndBiller(
                        billData.getUser().getId(), billData.getCategory(), billData.getBiller());
                Collections.reverse(previousDataSubList);
                int dataIndex = 0;
                for (BillData data : previousDataSubList){
                    if (dataList.contains(data)){
                        dataIndex = dataList.indexOf(data);
                        break;
                    }
                }

                List<BillData> subDataList;
                if (dataIndex < 1){
                    return;
                }
                else if (dataIndex < 3){
                    subDataList = dataList.subList(0, dataIndex + 1);
                    subDataList.add(eachBillData);
                }
                else {
                    subDataList = dataList.subList(dataIndex - 3, dataIndex + 1);
                    subDataList.add(eachBillData);
                }
                addNonSeasonal(eachBillData, subDataList, userStatsList);
            }

            double a = Math.abs(eachBillData.getMonthlyAmount() - eachBillData.getPredictedAmount());
            double b = userStatsList.get(0).getStandardDeviation();
            eachBillData.setStatus(a <= b);
            edit(eachBillData, eachBillData.getId());
            updateUserStats(eachBillData);
        }
    }

    public void modifyFollowingSeasonalData(BillData billData){
        List<BillData> billDataList = billDataRepository.getBillDataByUserIdAndCategoryAndBiller(
                billData.getUser().getId(), billData.getCategory(), billData.getBiller());
        int index = billDataList.indexOf(billData);
        List<BillData> followingDataSubList = billDataList.subList(index + 1, billDataList.size());
        for (BillData eachBillData : followingDataSubList){
            List<UserStats> userStatsList = userStatsRepository.getUserStatsByUserIdAndCategoryAndBiller(
                    billData.getUser().getId(), billData.getCategory(), billData.getBiller());
            addSeasonal(eachBillData, userStatsList);
            edit(eachBillData, eachBillData.getId());
            updateUserStats(eachBillData);
        }
    }

    public double calculateCentroid(List<BillData> billDataList){
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (BillData billData : billDataList){
            stats.addValue(billData.getMonthlyAmount());
        }
        return stats.getMean();
    }

    public List<Map> clusters(List<BillData> mainBillDataList, int k) {
        List<BillData> billDataList = new ArrayList<>(mainBillDataList);
        Map<Integer, List<BillData>> cluster = new HashMap<>();
        Map<Integer, Double> centroid = new HashMap<>();
        for (int i = 0; i < k; i++) {
            List<BillData> list = new ArrayList<>();
            list.add(mainBillDataList.get(i));
            System.out.println(mainBillDataList.get(i));
            double value = mainBillDataList.get(i).getMonthlyAmount();
            cluster.put(i, list);
            centroid.put(i, value);
            billDataList.removeAll(list);
        }

        if (!billDataList.isEmpty()) {
            for (BillData billData : billDataList) {
                List<Double> distanceList = new ArrayList<>();
                for (int i = 0; i < k; i++) {
                    double distance = Math.abs(billData.getMonthlyAmount() - centroid.get(i));
                    distanceList.add(distance);
                }
                int index = distanceList.indexOf(Collections.min(distanceList));
                List<BillData> dataList = cluster.get(index);
                dataList.add(billData);
                double updatedCentroid = calculateCentroid(dataList);
                centroid.put(index, updatedCentroid);
            }
        }

        Map<Integer, List<BillData>> lastState = new HashMap<>();
        boolean maxIteration = true;
        int count = 0;
        while (maxIteration) {
            for (int i = 0; i < k; i++) {
                List<BillData> dataList = cluster.get(i);
                List<BillData> clone = new ArrayList<>(dataList);
                lastState.put(i, clone);
                System.out.println(dataList);
                if (dataList.size() > 1 && dataList.size() != mainBillDataList.size()) {
                    List<BillData> listOfBillData = new ArrayList<>(dataList);
                    for (BillData billData : listOfBillData) {
                        double currentDistance = Math.abs(billData.getMonthlyAmount() - centroid.get(i));
                        List<Double> newDistanceList = new ArrayList<>();

                        for (int j = 0; j < k; j++) {
                            if (j != i) {
                                double newDistance = Math.abs(billData.getMonthlyAmount() - centroid.get(j));
                                newDistanceList.add(newDistance);
                                System.out.println(newDistance);
                            } else {
                                newDistanceList.add(Double.MAX_VALUE);
                            }
                        }

                        System.out.println(newDistanceList);
                        double newDistance = Collections.min(newDistanceList);
                        System.out.println("chosen " + newDistance);
                        System.out.println(currentDistance);
                        if (newDistance < currentDistance) {
                            int index = newDistanceList.indexOf(newDistance);
                            cluster.get(index).add(billData);
                            double newCentroid = calculateCentroid(cluster.get(index));
                            centroid.put(index, newCentroid);
                            System.out.println(cluster.get(index));

                            dataList.remove(billData);
                            double updatedCentroid = calculateCentroid(dataList);
                            centroid.put(i, updatedCentroid);
                            System.out.println(dataList);
                        }
                    }
                }
            }
            System.out.println(lastState);
            System.out.println(cluster);

            if (cluster.equals(lastState)) {
                maxIteration = false;
                System.out.println("FINISH");
            } else {
                lastState = cluster;
                System.out.println("AGAIN");
                count++;
            }
        }
        System.out.println("iteration count: " + count);

        List<Map> maps = new ArrayList<>();
        maps.add(cluster);
        maps.add(centroid);
        return maps;
    }

    public double calculateDistP2C(Map<Integer, List<BillData>> cluster, Map<Integer, Double> centroid, int k){
        double distP2C = 0.0;
        for (int i = 0; i < k; i++) {
            List<BillData> dataList = cluster.get(i);
            double value = centroid.get(i);
            for (BillData billData : dataList){
                distP2C += Math.abs(billData.getMonthlyAmount() - value);
            }
        }
        System.out.println("dist_points_from_cluster_center: " + distP2C);
        return distP2C;
    }

    public double calculateDistance(int k, double distP2C, double a, int b, double c){
        return Math.abs((a * k + b * distP2C + c)) / (Math.sqrt(a * a + b * b));
    }

    public List<BillData> predict(List<BillData> billDataList, int k, BillData billData){
        List<Double> sumList = new ArrayList<>();
        List<Map> clusters = new ArrayList<>();
        List<Map> centroids = new ArrayList<>();
        for (int i = 1; i <= k; i++) {
            List<Map> calculateCluster = clusters(billDataList, i);
            Map<Integer, List<BillData>> cluster = calculateCluster.get(0);
            Map<Integer, Double> centroid = calculateCluster.get(1);
            sumList.add(calculateDistP2C(cluster, centroid, i));
            clusters.add(cluster);
            centroids.add(centroid);
        }

        List<Double> resultList = new ArrayList<>();
        double a = sumList.get(0) - sumList.get(sumList.size() - 1);
        int b = k - 1;
        double c1 = 1 * sumList.get(sumList.size() - 1);
        double c2 = k * sumList.get(0);
        double c = c1 - c2;
        for (int i = 1; i <= k; i++) {
            resultList.add(calculateDistance(i, sumList.get(i-1), a, b, c));
        }
        System.out.println(resultList);

        int resultIndex = resultList.indexOf(Collections.max(resultList));
        System.out.println(resultIndex);
        Map<Integer, List<BillData>> chosenCluster = clusters.get(resultIndex);
        Map<Integer, Double> chosenCentroid = centroids.get(resultIndex);

        billData.setCluster(resultIndex);

        List<BillData> chosenBillDataList = new ArrayList<>();
        if (chosenCluster.size() == 1){
            billData.setPredictedAmount(chosenCentroid.get(0));
            chosenBillDataList = chosenCluster.get(0);
            System.out.println(billData.getPredictedAmount());
        }

        else if (chosenCluster.size() == billDataList.size()){
            DescriptiveStatistics stats = new DescriptiveStatistics();
            for (int i = 0; i < chosenCluster.size(); i++) {
                stats.addValue(chosenCentroid.get(i));
            }
            billData.setPredictedAmount(stats.getMean());
        }

        else {
            List<Integer> sizeList = new ArrayList<>();
            List<Integer> indexList = new ArrayList<>();
            for (int i = 0; i < chosenCluster.size(); i++) {
                sizeList.add(chosenCluster.get(i).size());
            }
            for (int i = 0; i < chosenCluster.size(); i++) {
                int clusterSize = sizeList.get(i);
                if (clusterSize == Collections.max(sizeList)){
                    indexList.add(i);
                }
            }

            double centroid = Double.MAX_VALUE;
            for (int index : indexList) {
                if (chosenCentroid.get(index) < centroid){
                    centroid = chosenCentroid.get(index);
                    chosenBillDataList = chosenCluster.get(index);
                }
            }
            System.out.println(centroid);
            billData.setPredictedAmount(centroid);
        }

        return chosenBillDataList;
    }

    @CrossOrigin
    @GetMapping("/bill_data/test/{k}")
    public void test(@PathVariable("k") final int k){
        BillData billData = new BillData();
        billData.setMonthlyAmount(93);
        BillData billData1 = new BillData();
        billData1.setMonthlyAmount(93);
        BillData billData2 = new BillData();
        billData2.setMonthlyAmount(93);
        BillData billData3 = new BillData();
        billData3.setMonthlyAmount(93);
        BillData billData4 = new BillData();
        billData4.setMonthlyAmount(93);

        List<BillData> billDataList = new ArrayList<>();
        billDataList.add(billData);
        billDataList.add(billData1);
        billDataList.add(billData2);
        billDataList.add(billData3);
        billDataList.add(billData4);

        for (BillData billData5 : billDataList){
            System.out.println(billData5.getMonthlyAmount());
        }

        predict(billDataList, 5, billData4);
    }

    @CrossOrigin
    @GetMapping ("/bill_data/status")
    public List<Integer> getNumberOfBillDataByStatus(){
        int numberOfTrueBillData = billDataRepository.getTrueBillData().size();
        int numberOfFalseBillData = billDataRepository.getFalseBillData().size();
        List<Integer> list = new ArrayList<>();
        list.add(numberOfTrueBillData);
        list.add(numberOfFalseBillData);
        return list;
    }
}
