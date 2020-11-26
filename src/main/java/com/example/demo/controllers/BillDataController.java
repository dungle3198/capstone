package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.repositories.BillDataRepository;
import com.example.demo.repositories.LogRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.UserStatsRepository;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BillDataController {

    private final BillDataRepository billDataRepository;
    private final UserRepository userRepository;
    private final LogRepository logRepository;
    private final UserController userController;
    private final UserStatsRepository userStatsRepository;

    @Autowired
    public BillDataController(BillDataRepository billDataRepository, UserRepository userRepository, LogRepository logRepository, UserController userController, UserStatsRepository userStatsRepository) {
        this.billDataRepository = billDataRepository;
        this.userRepository = userRepository;
        this.logRepository = logRepository;
        this.userController = userController;
        this.userStatsRepository = userStatsRepository;
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
    public BillData getBillDataById(@PathVariable ("id") final int id){
        return billDataRepository.findById(id).get();
    }

    public List<Integer> getListOfUserId(){
        return userRepository.getListOfUserId();
    }

    @CrossOrigin
    @GetMapping ("/bill_data/mean_month/user/{id}")
    public Map<String, List> getCategoryMeanMonthByUserId(@PathVariable ("id") final int id){
        Map<String, List> mapOfLists = new HashMap<>();
        //Map<String, List> mapOfStats = getStatisticsDataByUserId(id);
        List<UserStats> userStatsList = userStatsRepository.getUserStatsByUserId(id);
        List<String> categoryList = new ArrayList<>();
        List<String> billerList = new ArrayList<>();
        List<Double> meanList = new ArrayList<>();
        List<Double> standardDeviationList = new ArrayList<>();
        for (UserStats userStats : userStatsList){
            categoryList.add(userStats.getCategory());
            billerList.add(userStats.getBiller());
            meanList.add(userStats.getMean());
            standardDeviationList.add(userStats.getStandardDeviation());
        }
        List<String> seasonalCategoryList = new ArrayList<>();
        List<List<Double>> listOfMeanMonthLists = new ArrayList<>();

        for (int i = 0; i < categoryList.size(); i++) {
            double result = Math.abs((standardDeviationList.get(i) / meanList.get(i)));
            System.out.println(result);
            if (result > 0.05){
                List<Double> meanMonthList = new ArrayList<>(Collections.nCopies(12, 0.0));
                List<Integer> frequencyList = new ArrayList<>(Collections.nCopies(12, 0));
                List<BillData> billDataList = billDataRepository.getBillDataWithDate(id,
                                                    categoryList.get(i), billerList.get(i));
                if (billDataList.isEmpty()){ continue; }

                List<BillData> processedBillDataList = calculatePercentiles(billDataList);
                for (BillData billData : processedBillDataList){
                    int index = billData.getMonth() - 1;
                    meanMonthList.set(index, meanMonthList.get(index) + billData.getAmount());
                    frequencyList.set(index, frequencyList.get(index) + 1);
                }

                for (int j = 0; j < meanMonthList.size(); j++) {
                    if (frequencyList.get(j) != 0) {
                        meanMonthList.set(j, meanMonthList.get(j) / frequencyList.get(j));
                    }
                }
                seasonalCategoryList.add(categoryList.get(i) + " " + billerList.get(i));
                listOfMeanMonthLists.add(meanMonthList);
            }
        }

        mapOfLists.put("seasonalCategory", seasonalCategoryList);
        mapOfLists.put("meanMonthList", listOfMeanMonthLists);
        System.out.println(seasonalCategoryList);
        System.out.println(listOfMeanMonthLists);
        return mapOfLists;
    }

    public List<BillData> calculatePercentiles(List<BillData> billDataList){
        DescriptiveStatistics stats = new DescriptiveStatistics();
        billDataList.sort(Comparator.comparing(BillData::getAmount));
        for (BillData billData : billDataList){
            stats.addValue(billData.getAmount());
        }
        double q1 = stats.getPercentile(25);
        double q3 = stats.getPercentile(75);
        double iqr = q3 - q1;

        billDataList.removeIf(billData -> billData.getAmount() < q1 - 1.5 * iqr || billData.getAmount() > q3 + 1.5 * iqr);
        return billDataList;
    }

    public List<Double> calculateMeanAndStandardDeviation(BillData billData){
        DescriptiveStatistics stats = new DescriptiveStatistics();
        List<Double> statsList = new ArrayList<>();
        User user = billData.getUser();
        List<BillData> billDataList = billDataRepository.getBillDataByUserIdAndCategoryAndBiller(user.getId(),
                                                        billData.getCategory(), billData.getBiller());
        List<BillData> processedBillDataList = calculatePercentiles(billDataList);

        for (BillData eachBillData : processedBillDataList){
            stats.addValue(eachBillData.getAmount());
        }
        double mean = stats.getMean();
        double standardDeviation = stats.getStandardDeviation();
        statsList.add(mean);
        statsList.add(standardDeviation);

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
            newUserStats.setMean(billData.getAmount());
            newUserStats.setStandardDeviation(0);
            userStatsRepository.save(newUserStats);
        }
        else {
            UserStats userStats = userStatsList.get(0);
            List<Double> statsList = calculateMeanAndStandardDeviation(billData);
            userStats.setNumberOfBills(userStats.getNumberOfBills() + 1);
            userStats.setMean(statsList.get(0));
            userStats.setStandardDeviation(statsList.get(1));
            userStatsRepository.save(userStats);
        }
    }

//    public void predict (List<BillData> billDataList, BillData billData){
//        if (billData.getAmount() == billDataList.get(billDataList.size() - 1).getAmount() &&
//                billData.getAmount() == billDataList.get(billDataList.size() - 2).getAmount()){
//            billData.setPredictedAmount(billData.getAmount() * (-1));
//        }
//        else {
//            List<Double> monthlyAmountList = new ArrayList<>();
//            List<Double> listOfMaxValues = new ArrayList<>();
//            List<Integer> frequencyList = new ArrayList<>();
//            for (BillData eachBillData : billDataList){
//                monthlyAmountList.add(eachBillData.getMonthlyAmount());
//            }
//
//            for (Double monthlyAmount : monthlyAmountList){
//                int frequency = Collections.frequency(monthlyAmountList, monthlyAmount);
//                frequencyList.add(frequency);
//            }
//            int max = Collections.max(frequencyList);
//
//            for (int i = 0; i < monthlyAmountList.size(); i++) {
//                if (frequencyList.get(i) == max){
//                    listOfMaxValues.add(monthlyAmountList.get(i));
//                }
//            }
//            double mean = listOfMaxValues.stream().mapToDouble(val -> val).average().orElse(0.0);
//            billData.setPredictedAmount(mean);
//        }
//    }

    @CrossOrigin
    @PostMapping ("/bill_data")
    public void add(@RequestBody BillData billData){
        List<BillData> billDataList1;
        List<BillData> billDataList2;
        if (billData.getUser() == null || !getListOfUserId().contains(billData.getUser().getId())){
            User user = new User();
            userController.add(user);
            billData.setUser(user);
            billDataList1 = billDataRepository.getBillDataWithDate(user.getId(),
                    billData.getCategory(), billData.getBiller());
            billDataList2 = billDataRepository.getBillDataByUserIdAndCategoryAndBiller(
                    user.getId(), billData.getCategory(), billData.getBiller());
        }
        else {
            billDataList1 = billDataRepository.getBillDataWithDate(billData.getUser().getId(),
                    billData.getCategory(), billData.getBiller());
            billDataList2 = billDataRepository.getBillDataByUserIdAndCategoryAndBiller(
                    billData.getUser().getId(), billData.getCategory(), billData.getBiller());
        }

        if (billData.getMonth() == 0 || billData.getYear() == 0){
            billData.setPeriod(1);
            billData.setMonthlyAmount(billData.getAmount() * (-1));
        }
        else if (!billDataList1.isEmpty()){
            BillData existingBillData = billDataList1.get(billDataList1.size() - 1);
            int month = existingBillData.getMonth();
            int year = existingBillData.getYear();
            int period = billData.getMonth() + ((billData.getYear() - year) * 12) - month;
            billData.setPeriod(period);
            billData.setMonthlyAmount(billData.getAmount() / period);
            if (billDataList1.size() == 1) {
                existingBillData.setPeriod(period);
                existingBillData.setMonthlyAmount(existingBillData.getAmount() / period);
                edit(existingBillData, existingBillData.getId());
            }
        }

        if (billDataList2.size() >= 5){
            List<BillData> subList = billDataList2.subList(billDataList2.size() - 4, billDataList2.size());
            subList.add(billData);
            billData.setPredictedAmount(predict(subList, 5, billData));
            double monthlyAmount = Math.abs(billData.getAmount());
            double predictedAmount = Math.abs(billData.getPredictedAmount());
            double min = Math.min(monthlyAmount - 5, monthlyAmount - 0.05 * monthlyAmount);
            double max = Math.max(monthlyAmount + 5, monthlyAmount + 0.05 * monthlyAmount);
            billData.setStatus(predictedAmount >= min && predictedAmount <= max);
        }
        else {
            billData.setPredictedAmount(0);
            billData.setStatus(true);
        }

        billDataRepository.save(billData);
        if (billData.isStatus()){
            updateUserStats(billData);
        }

        //Log
        String description = "New bill " + billData.getId() + " is added";
        Log activityLog = new Log("bg-primary", DateTime.now().toString(), billData.getId(), description);
        logRepository.save(activityLog);
    }

//    @CrossOrigin
//    @PostMapping("/bill_data")
//    public void add(@RequestBody BillData billData){
//        User user = userRepository.findById(billData.getUser().getId()).get();
//        billData.setUser(user);
//        billData.setStatus(true);
//        billDataRepository.save(billData);
//    }

    @CrossOrigin
    @PutMapping ("/bill_data/{id}")
    public void edit(@RequestBody BillData billData, @PathVariable("id") final int id){
        BillData existingBillData = getBillDataById(id);
        User user = userRepository.findById(existingBillData.getUser().getId()).get();
        existingBillData.setId(billData.getId());
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

        //Log
        String description = "Bill " + id + " is edited";
        Log activityLog = new Log("bg-warning", DateTime.now().toString(), id, description);
        logRepository.save(activityLog);

    }

    @CrossOrigin
    @DeleteMapping ("/bill_data/{id}")
    public void delete(@PathVariable ("id") final int id){
        billDataRepository.deleteById(id);

        //Log
        String description = "Bill " + id + " is deleted";
        Log activityLog = new Log("bg-danger", DateTime.now().toString(), id, description);
        logRepository.save(activityLog);
    }

    @CrossOrigin
    @GetMapping ("bill_data/confirm/{id}")
    public void confirmBillData (@PathVariable ("id") final int id){
        BillData billData = billDataRepository.findById(id).get();
        billData.setStatus(!billData.isStatus());
        billDataRepository.save(billData);
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

    public double calculateCentroid(List<BillData> billDataList){
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (BillData billData : billDataList){
            stats.addValue(billData.getAmount());
        }
        return stats.getMean();
    }

    public List<Map> clusters(List<BillData> mainBillDataList, int k){
        List<BillData> billDataList = new ArrayList<>(mainBillDataList);
        Map<Integer, List<BillData>> cluster = new HashMap<>();
        Map<Integer, Double> centroid = new HashMap<>();
        for (int i = 0; i < k; i++) {
            List<BillData> list = new ArrayList<>();
            list.add(mainBillDataList.get(i));
            System.out.println(mainBillDataList.get(i));
            double value = mainBillDataList.get(i).getAmount();
            cluster.put(i, list);
            centroid.put(i, value);
            billDataList.removeAll(list);
        }

        if (!billDataList.isEmpty()){
            for (BillData billData : billDataList){
                List<Double> distanceList = new ArrayList<>();
                for (int i = 0; i < k; i++) {
                    double distance = Math.abs(billData.getAmount() - centroid.get(i));
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
        while (maxIteration){
            for (int i = 0; i < k; i++) {
                List<BillData> dataList = cluster.get(i);
                List<BillData> clone = new ArrayList<>(dataList);
                lastState.put(i, clone);
                System.out.println(dataList);
                if (dataList.size() > 1 && dataList.size() != mainBillDataList.size()){
                    List<BillData> listOfBillData = new ArrayList<>(dataList);
                    for (BillData billData : listOfBillData){
                        double currentDistance = Math.abs(billData.getAmount() - centroid.get(i));
                        List<Double> newDistanceList = new ArrayList<>();

                        for (int j = 0; j < k; j++) {
                            if (j != i){
                                double newDistance = Math.abs(billData.getAmount() - centroid.get(j));
                                newDistanceList.add(newDistance);
                                System.out.println(newDistance);
                            }
                            else {newDistanceList.add(Double.MAX_VALUE);}
                        }

                        System.out.println(newDistanceList);
                        double newDistance = Collections.min(newDistanceList);
                        System.out.println("chosen " + newDistance);
                        System.out.println(currentDistance);
                        if (newDistance < currentDistance){
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

            if (cluster.equals(lastState)){
                maxIteration = false;
                System.out.println("FINISH");
            }
            else {
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
                distP2C += Math.abs(billData.getAmount() - value);
            }
        }
        System.out.println("dist_points_from_cluster_center: " + distP2C);
        return distP2C;
    }

    public double calculateDistance(int k, double distP2C, double a, int b, double c){
        return Math.abs((a * k + b * distP2C + c)) / (Math.sqrt(a * a + b * b));
    }

    public double predict(List<BillData> billDataList, int k, BillData billData){
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
        for (int i = 0; i < k; i++) {
            resultList.add(calculateDistance(i + 1, sumList.get(i), a, b, c));
        }
        System.out.println(resultList);

        int resultIndex = resultList.indexOf(Collections.max(resultList));
        Map<Integer, List<BillData>> chosenCluster = clusters.get(resultIndex);
        Map<Integer, Double> chosenCentroid = centroids.get(resultIndex);
        List<Integer> sizeList = new ArrayList<>();

        billData.setCluster(resultIndex);

        if (chosenCluster.size() == 1){
            return chosenCentroid.get(0);
        }
        else if (chosenCluster.size() == 5){
            DescriptiveStatistics stats = new DescriptiveStatistics();
            for (int i = 0; i < chosenCluster.size(); i++) {
                stats.addValue(chosenCentroid.get(i));
            }
            return stats.getMean();
        }
        else {
            for (int i = 0; i < chosenCluster.size(); i++) {
                sizeList.add(chosenCluster.get(i).size());
            }
            int index = sizeList.indexOf(Collections.max(sizeList));
            System.out.println(chosenCentroid);
            System.out.println(chosenCentroid.get(index));
            return chosenCentroid.get(index);
        }
    }

    @CrossOrigin
    @GetMapping("/bill_data/test/{k}")
    public List<Double> test(@PathVariable("k") final int k){
        BillData billData = new BillData();
        billData.setAmount(277);
        BillData billData1 = new BillData();
        billData1.setAmount(284);
        BillData billData2 = new BillData();
        billData2.setAmount(342);
        BillData billData3 = new BillData();
        billData3.setAmount(328);
        BillData billData4 = new BillData();
        billData4.setAmount(334);

        List<BillData> billDataList = new ArrayList<>();
        billDataList.add(billData);
        billDataList.add(billData1);
        billDataList.add(billData2);
        billDataList.add(billData3);
        billDataList.add(billData4);

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
        for (int i = 0; i < k; i++) {
            resultList.add(calculateDistance(i + 1, sumList.get(i), a, b, c));
        }
        System.out.println(resultList);

        int index = resultList.indexOf(Collections.max(resultList));
        Map<Integer, List<BillData>> chosenCluster = clusters.get(index);
        Map<Integer, Double> chosenCentroid = centroids.get(index);
        List<Integer> sizeList = new ArrayList<>();
        for (int i = 0; i < chosenCluster.size(); i++) {
            sizeList.add(chosenCluster.get(i).size());
        }

        int index1 = sizeList.indexOf(Collections.max(sizeList));
        System.out.println(index);
        for (List<BillData> dataList : chosenCluster.values()){
            for (BillData billData5 : dataList){
                System.out.println(billData5.getAmount());
            }
        }
        System.out.println(chosenCluster);
        System.out.println(chosenCentroid);
        System.out.println(chosenCentroid.get(index1));

        return sumList;
    }
}
