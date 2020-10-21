package com.example.demo.controllers;

import com.example.demo.entities.Bill;
import com.example.demo.entities.BillData;
import com.example.demo.entities.User;
import com.example.demo.repositories.BillDataRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BillDataController {

    private final BillDataRepository billDataRepository;
    private final UserRepository userRepository;

    @Autowired
    public BillDataController(BillDataRepository billDataRepository, UserRepository userRepository) {
        this.billDataRepository = billDataRepository;
        this.userRepository = userRepository;
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
    @GetMapping ("/bill_data/count/user/{id}")
    public Map<String, List> getNumberOfBillsByCategoryAndBiller(@PathVariable ("id") final int id){
        Map<String, List> mapOfLists = new HashMap<>();
        List<BillData> billDataList = billDataRepository.getBillDataByUserId(id);
        List<String> categoryList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();

        for (BillData eachBillData : billDataList){
            String categoryAndBiller = eachBillData.getCategory().toLowerCase() + " " +
                                        eachBillData.getBiller().toLowerCase();
            if (!categoryList.contains(categoryAndBiller)){
                categoryList.add(categoryAndBiller);
                countList.add(0);
            }
        }

        for (BillData eachBillData : billDataList){
            String categoryAndBiller = eachBillData.getCategory().toLowerCase() + " " +
                                        eachBillData.getBiller().toLowerCase();
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryAndBiller.equals(categoryList.get(i))){
                    countList.set(i, countList.get(i) + 1);
                }
            }
        }

        mapOfLists.put("categoryAndBiller", categoryList);
        mapOfLists.put("numberOfBills", countList);
        System.out.println(categoryList);
        System.out.println(countList);
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
    }
}
