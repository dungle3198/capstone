package com.example.demo.controllers;

import com.example.demo.entities.Bill;
import com.example.demo.entities.BillData;
import com.example.demo.entities.Log;
import com.example.demo.entities.User;
import com.example.demo.repositories.BillDataRepository;
import com.example.demo.repositories.LogRepository;
import com.example.demo.repositories.UserRepository;
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

    @CrossOrigin
    @GetMapping ("/bill_data/stats/user/{id}")
    public Map<String, List> getStatisticsDataByUserId(@PathVariable ("id") final int id){
        Map<String, List> mapOfLists = new HashMap<>();
        List<BillData> billDataList = billDataRepository.getBillDataByUserId(id);
        DescriptiveStatistics stats = new DescriptiveStatistics();
        List<String> categoryAndBillerList = new ArrayList<>();
        List<Integer> numberOfBillsList = new ArrayList<>();
        List<Double> meanList = new ArrayList<>();
        List<Double> standardDeviationList = new ArrayList<>();

        for (BillData eachBillData : billDataList){
            String categoryAndBiller = eachBillData.getCategory().toLowerCase() + " " +
                                        eachBillData.getBiller().toLowerCase();
            if (!categoryAndBillerList.contains(categoryAndBiller)){
                categoryAndBillerList.add(categoryAndBiller);
                List<BillData> listOfBillData = billDataRepository.getBillDataByUserIdAndCategoryAndBiller(id,
                        eachBillData.getCategory(), eachBillData.getBiller());
                numberOfBillsList.add(listOfBillData.size());
                for (BillData everyBillData : listOfBillData){
                    stats.addValue(everyBillData.getAmount());
                }
                double mean = stats.getMean();
                double standardDeviation = stats.getStandardDeviation();
                meanList.add(mean);
                standardDeviationList.add(standardDeviation);
            }
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
        List<BillData> billDataList1 = billDataRepository.getBillDataList(billData.getUser().getId(),
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
