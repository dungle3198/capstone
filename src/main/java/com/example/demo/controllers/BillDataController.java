package com.example.demo.controllers;

import com.example.demo.entities.BillData;
import com.example.demo.entities.Log;
import com.example.demo.entities.User;
import com.example.demo.repositories.BillDataRepository;
import com.example.demo.repositories.LogRepository;
import com.example.demo.repositories.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BillDataController {

    private final BillDataRepository billDataRepository;
    private final UserRepository userRepository;
    private final LogRepository logRepository;

    @Autowired
    public BillDataController(BillDataRepository billDataRepository, UserRepository userRepository, LogRepository logRepository) {
        this.billDataRepository = billDataRepository;
        this.userRepository = userRepository;
        this.logRepository = logRepository;
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

    public List<String> getListOfCategoryAndBiller(int id){
        List<BillData> billDataList = getBillDataByUserId(id);
        List<String> categoryAndBillerList = new ArrayList<>();
        for (BillData billData : billDataList) {
            String categoryAndBiller = billData.getCategory() + " by " + billData.getBiller();
            if (!categoryAndBillerList.contains(categoryAndBiller)) {
                categoryAndBillerList.add(categoryAndBiller);
            }
        }
        return categoryAndBillerList;
    }

    @CrossOrigin
    @GetMapping ("/bill_data/stats/user/{id}")
    public Map<String, List> getStatisticsDataByUserId(@PathVariable ("id") final int id){
        Map<String, List> mapOfLists = new HashMap<>();
        List<String> categoryAndBillerList = getListOfCategoryAndBiller(id);
        List<Integer> numberOfBillsList = new ArrayList<>();
        List<Double> meanList = new ArrayList<>();
        List<Double> standardDeviationList = new ArrayList<>();

        for (String categoryAndBiller : categoryAndBillerList){
            List<Double> amountList = billDataRepository.getBillAmountByUserIdAndCategoryAndBiller(id,
                    categoryAndBiller.split(" by ")[0], categoryAndBiller.split(" by ")[1]);

            double mean = amountList.stream().mapToDouble(val -> val).average().orElse(0.0);
            double variance = amountList.stream().map(i -> i - mean).map(i -> i*i).
                                        mapToDouble(i -> i).average().orElse(0.0);
            double standardDeviation = Math.sqrt(variance);

            numberOfBillsList.add(amountList.size());
            meanList.add(mean);
            standardDeviationList.add(standardDeviation);
        }

        mapOfLists.put("categoryAndBiller", categoryAndBillerList);
        mapOfLists.put("numberOfBills", numberOfBillsList);
        mapOfLists.put("mean", meanList);
        mapOfLists.put("standardDeviation", standardDeviationList);
        System.out.println(categoryAndBillerList);
        System.out.println(numberOfBillsList);
        System.out.println(meanList);
        System.out.println(standardDeviationList);
        return mapOfLists;
    }

    @CrossOrigin
    @GetMapping ("/bill_data/mean_month/user/{id}")
    public Map<String, List> getCategoryMeanMonthByUserId(@PathVariable ("id") final int id){
        Map<String, List> mapOfLists = new HashMap<>();
        Map<String, List> mapOfStats = getStatisticsDataByUserId(id);
        List<String> seasonalCategoryList = new ArrayList<>();
        List<String> categoryAndBillerList = mapOfStats.get("categoryAndBiller");
        List<Double> meanList = mapOfStats.get("mean");
        List<Double> standardDeviationList = mapOfStats.get("standardDeviation");
        List<List<Double>> listOfMeanMonthLists = new ArrayList<>();

        for (int i = 0; i < categoryAndBillerList.size(); i++) {
            double result = (standardDeviationList.get(i) / meanList.get(i)) * (-1);
            System.out.println(result);
            if (result > 0.05){
                String categoryAndBiller = categoryAndBillerList.get(i);
                seasonalCategoryList.add(categoryAndBillerList.get(i));
                List<Double> meanMonthList = new ArrayList<>(Collections.nCopies(12, 0.0));
                List<Integer> frequencyList = new ArrayList<>(Collections.nCopies(12, 0));
                List<BillData> billDataList = billDataRepository.getBillDataByUserIdAndCategoryAndBiller(id,
                        categoryAndBiller.split(" by ")[0], categoryAndBiller.split(" by ")[1]);

                for (BillData billData : billDataList){
                    int index = billData.getMonth() - 1;
                    double amount = billData.getAmount();
                    meanMonthList.set(index, meanMonthList.get(index) + amount);
                    frequencyList.set(index, frequencyList.get(index) + 1);
                }

                for (int j = 0; j < 12; j++) {
                    if (frequencyList.get(j) != 0) {
                        meanMonthList.set(j, meanMonthList.get(j) / frequencyList.get(j));
                    }
                }
                listOfMeanMonthLists.add(meanMonthList);
            }
        }

        mapOfLists.put("seasonalCategory", seasonalCategoryList);
        mapOfLists.put("meanMonthList", listOfMeanMonthLists);
        System.out.println(seasonalCategoryList);
        System.out.println(listOfMeanMonthLists);
        return mapOfLists;
    }

    public void predict (List<BillData> billDataList, BillData billData){
        if (billData.getAmount() == billDataList.get(billDataList.size() - 1).getAmount() &&
                billData.getAmount() == billDataList.get(billDataList.size() - 2).getAmount()){
            billData.setPredictedAmount(billData.getAmount() * (-1));
        }
        else {
            List<Double> monthlyAmountList = new ArrayList<>();
            List<Double> listOfMaxValues = new ArrayList<>();
            List<Integer> frequencyList = new ArrayList<>();
            for (BillData eachBillData : billDataList){
                monthlyAmountList.add(eachBillData.getMonthlyAmount());
            }

            for (Double monthlyAmount : monthlyAmountList){
                int frequency = Collections.frequency(monthlyAmountList, monthlyAmount);
                frequencyList.add(frequency);
            }
            int max = Collections.max(frequencyList);

            for (int i = 0; i < monthlyAmountList.size(); i++) {
                if (frequencyList.get(i) == max){
                    listOfMaxValues.add(monthlyAmountList.get(i));
                }
            }
            double mean = listOfMaxValues.stream().mapToDouble(val -> val).average().orElse(0.0);
            billData.setPredictedAmount(mean);
        }
    }

    @CrossOrigin
    @PostMapping ("/bill_data")
    public void add(@RequestBody BillData billData){
        List<BillData> billDataList1 = billDataRepository.getBillDataWithDate(billData.getUser().getId(),
                                                        billData.getCategory(), billData.getBiller());
        List<BillData> billDataList2 = billDataRepository.getBillDataByUserIdAndCategoryAndBiller(
                            billData.getUser().getId(), billData.getCategory(), billData.getBiller());

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
            List<BillData> billDataSubList = billDataList2.subList(billDataList2.size() - 5, billDataList2.size());
            predict(billDataSubList, billData);
        }
        else {
            billData.setPredictedAmount(0);
        }
        billDataRepository.save(billData);

        //Log
        String description = "New bill " + billData.getId() + " is added.";
        Log activityLog = new Log("Bill", DateTime.now().toString(),billData.getId(),description);
        logRepository.save(activityLog);
    }

    @CrossOrigin
    @PutMapping ("/bill_data/{id}")
    public void edit(@RequestBody BillData billData, @PathVariable("id") final int id){
        BillData existingBillData = billDataRepository.findById(id).get();
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
        String description = "Bill " + billData.getId() + " is edited.";
        Log activityLog = new Log("Bill", DateTime.now().toString(),billData.getId(),description);
        logRepository.save(activityLog);

    }
}
