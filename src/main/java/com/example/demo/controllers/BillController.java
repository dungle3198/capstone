package com.example.demo.controllers;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.example.demo.config.DynamoDBConfig;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BillController {

    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final UserController userController;
    private final UserMeanController userMeanController;
    private final UserStdController userStdController;
    private final ElectricityMeanMonthController electricityMeanMonthController;
    private final InternetMeanMonthController internetMeanMonthController;
    private final GasMeanMonthController gasMeanMonthController;
    private final WaterMeanMonthController waterMeanMonthController;
    private final ClusterController clusterController;


    @Autowired
    public BillController(BillRepository billRepository, UserRepository userRepository, UserController userController, UserMeanController userMeanController, UserStdController userStdController, ElectricityMeanMonthController electricityMeanMonthController, InternetMeanMonthController internetMeanMonthController, GasMeanMonthController gasMeanMonthController, WaterMeanMonthController waterMeanMonthController, ClusterController clusterController) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
        this.userController = userController;
        this.userMeanController = userMeanController;
        this.userStdController = userStdController;
        this.electricityMeanMonthController = electricityMeanMonthController;
        this.internetMeanMonthController = internetMeanMonthController;
        this.gasMeanMonthController = gasMeanMonthController;
        this.waterMeanMonthController = waterMeanMonthController;
        this.clusterController = clusterController;
    }

    public List<Integer> getListOfUserId(){
        return userRepository.getListOfUserId();
    }

    public List<Double> getBillAmountByUserIdAndType(int user_id, String type){
        return billRepository.getBillAmountByUserIdAndType(user_id, type);
    }

    public List<Double> getBillAmountByUserIdAndTypeAndMonth(int user_id, String type, int month){
        return billRepository.getBillAmountByUserIdAndTypeAndMonth(user_id, type, month);
    }

    public List<Integer> getBillIdByUserIdAndType(int user_id, String type){
        return billRepository.getBillIdByUserIdAndType(user_id, type);
    }

    public List<Integer> getBillIdByUserIdAndTypeAndMonth(int user_id, String type, int month){
        return billRepository.getBillIdByUserIdAndTypeAndMonth(user_id, type, month);
    }

    public List<Double> calculatePercentiles(List<Double> amounts){
        DescriptiveStatistics stats = new DescriptiveStatistics();
        Collections.sort(amounts);
        for (Double amount : amounts){
            stats.addValue(amount);
        }
        double q1 = stats.getPercentile(25);
        double q3 = stats.getPercentile(75);
        double iqr = q3 - q1;

        amounts.removeIf(amount -> amount < (q1 - 1.5 * iqr) || amount > (q3 + 1.5 * iqr));
        return amounts;
    }

    public void extractMeanMonth (Bill bill, List<Double> amounts){
        User user = bill.getUser();
        GasMeanMonth gasMeanMonth = user.getGasMeanMonth();
        InternetMeanMonth internetMeanMonth = user.getInternetMeanMonth();
        ElectricityMeanMonth electricityMeanMonth = user.getElectricityMeanMonth();
        WaterMeanMonth waterMeanMonth = user.getWaterMeanMonth();

        List<Double> sortedList = calculatePercentiles(amounts);
        user.setMeanMonthType(sortedList, bill.getType(), bill.getMonth());
        switch (bill.getType().toLowerCase()){
            case "phone and internet":
                internetMeanMonthController.editInternetMeanMonth(internetMeanMonth, internetMeanMonth.getId());
                break;
            case "gas":
                gasMeanMonthController.editGasMeanMonth(gasMeanMonth, gasMeanMonth.getId());
                break;
            case "electricity":
                electricityMeanMonthController.editElectricityMeanMonth(electricityMeanMonth, electricityMeanMonth.getId());
                break;
            case "water":
                waterMeanMonthController.editWaterMeanMonth(waterMeanMonth, waterMeanMonth.getId());
                break;
        }
    }

    public void extract (Bill bill, List<Double> amounts){
        User user = bill.getUser();
        UserMean userMean = user.getUserMean();
        UserStd userStd = user.getUserStd();
        List<Double> sortedList = calculatePercentiles(amounts);
        userMean.calculateMean(sortedList, bill.getType());
        userStd.calculateStd(sortedList, bill.getType());
        userMeanController.editMean(userMean, userMean.getId());
        userStdController.editStd(userStd, userStd.getId());
    }

    public void extractAll(Bill bill){
        List<Double> amountList1 = getBillAmountByUserIdAndType(bill.getUser().getId(), bill.getType());
        List<Double> amountList2 = getBillAmountByUserIdAndTypeAndMonth(bill.getUser().getId(), bill.getType(), bill.getMonth());
        extract(bill, amountList1);
        extractMeanMonth(bill, amountList2);
        Cluster cluster = bill.getUser().getCluster();
        if (cluster != null){
            cluster.calculateCluster();
            clusterController.edit(cluster, cluster.getId());
        }
    }

    public void update(Bill bill, boolean option){
        User user = bill.getUser();
        List<Double> amountList1 = getBillAmountByUserIdAndType(user.getId(), bill.getType());
        List<Double> amountList2 = getBillAmountByUserIdAndTypeAndMonth(user.getId(), bill.getType(), bill.getMonth());
        List<Integer> idList1 = getBillIdByUserIdAndType(user.getId(), bill.getType());
        List<Integer> idList2 = getBillIdByUserIdAndTypeAndMonth(user.getId(), bill.getType(), bill.getMonth());
        int index1 = idList1.indexOf(bill.getId());
        int index2 = idList2.indexOf(bill.getId());

        if (option)
        {
            amountList1.remove(index1);
            amountList2.remove(index2);
            if (amountList1.isEmpty()){
                amountList1.add(0.0);
            }
            if (amountList2.isEmpty()){
                amountList2.add(0.0);
            }
            extract(bill, amountList1);
        }
        
        else {
            amountList2.remove(index2);
            if (amountList2.isEmpty()){
                amountList2.add(0.0);
            }
        }
        extractMeanMonth(bill, amountList2);
    }

    public void labelNewUserBill (Bill bill){
        List<Cluster> clusters = clusterController.clusters();
        double shortestDistance = Double.MAX_VALUE;
        Cluster chosenCluster = null;
        for (Cluster cluster: clusters){
            double distance = cluster.getDistance(bill.getUser().getUserMean(), bill.getUser().getUserStd());
            if (distance < shortestDistance){
                shortestDistance = distance;
                chosenCluster = cluster;
            }
        }
        bill.setNewUserBillLabel(chosenCluster);
    }

    @CrossOrigin
    @GetMapping("/bills")
    public List<Bill> bills(){
        return billRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/bills/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable("id") final int id)
    {
        Bill bill = billRepository.findById(id).get();
        return ResponseEntity.ok().body(bill);
    }

    @CrossOrigin
    @GetMapping("/bills/user/{id}/{type}")
    public List<Bill> getBillsByUserIdAndType(@PathVariable("id") final int user_id,@PathVariable("id") final String type){
        return billRepository.getBillsByUserIdAndType(user_id,type);
    }

    @CrossOrigin
    @GetMapping("/bills/user/{id}")
    public List<Bill> getBillsByUserId(@PathVariable("id") final int user_id){
        return billRepository.getBillsByUserId(user_id);
    }

    @CrossOrigin
    @PostMapping("/bills")
    public void add(@RequestBody Bill newBill)
    {

        if (newBill.getUser() == null || !getListOfUserId().contains(newBill.getUser().getId())){
            userController.add(newBill.getUser());
        }
        User user = userRepository.findById(newBill.getUser().getId()).get();
        newBill.setUser(user);
        newBill.setMonth();
        if (user.getTotal_bill() == 0){
            newBill.setLabel(true);
        }
        else if (user.isNewUser()){
            labelNewUserBill(newBill);
        }
        else {newBill.setOldUserBillLabel();}
        //newBill.setLabel(true);
        billRepository.save(newBill);
        user.setTotal_bill(user.getBills().size());
        if (newBill.isLabel()) {
            extractAll(newBill);
        }
    }

    @CrossOrigin
    @PutMapping("/bills/{id}")
    public void edit(@RequestBody Bill bill, @PathVariable ("id") final Integer id) throws ParseException {
        if (!getListOfUserId().contains(bill.getUser().getId())) {
            throw new IndexOutOfBoundsException();
        }
        else {
            Bill existedBill = billRepository.findById(id).get();
            User user = userRepository.findById(bill.getUser().getId()).get();
            User existedUser = existedBill.getUser();
            Cluster cluster = user.getCluster();
            Cluster existedCluster = existedUser.getCluster();
            bill.setMonth();

            if (existedBill.isLabel()) {
                if (!bill.getType().equalsIgnoreCase(existedBill.getType()) ||
                        bill.getAmount() != existedBill.getAmount() ||
                        user.getId() != existedUser.getId()) {
                    update(existedBill, true);

                    if (existedCluster != null && cluster != existedCluster){
                        existedCluster.calculateCluster();
                        clusterController.edit(existedCluster, existedCluster.getId());
                    }
                } else if (bill.getMonth() != existedBill.getMonth()) {
                    update(existedBill, false);
                }
            }

            if (user.getId() != existedUser.getId()){
                existedUser.setTotal_bill(existedUser.getBills().size() - 1);
                user.setTotal_bill(user.getBills().size() + 1);
            }

            existedBill.setId(bill.getId());
            existedBill.setUser(user);
            existedBill.setDate(bill.getDate());
            existedBill.setAmount(bill.getAmount());
            existedBill.setType(bill.getType());
            existedBill.setNumber(bill.getNumber());
            existedBill.setLocation(bill.getLocation());
            existedBill.setMonth();

            if (user.isNewUser()){
                labelNewUserBill(existedBill);
            }
            else {existedBill.setOldUserBillLabel();}

            billRepository.save(existedBill);
            if (existedBill.isLabel()) {
                extractAll(existedBill);
            }
        }
    }

    @CrossOrigin
    @DeleteMapping("/bills/{id}")
    public void delete (@PathVariable ("id") final Integer id)
    {
        Bill bill = billRepository.findById(id).get();
        User user = bill.getUser();
        if (bill.isLabel()) {
            update(bill, true);
            Cluster cluster = user.getCluster();
            if (cluster != null) {
                cluster.calculateCluster();
                clusterController.edit(cluster, cluster.getId());
            }
        }
        billRepository.deleteById(id);
        user.setTotal_bill(user.getBills().size());
        userRepository.save(user);
    }

    @CrossOrigin
    @GetMapping("/bills/confirm/{id}")
    public Bill confirmBill(@PathVariable("id") final int bill_id){
        Bill bill = billRepository.findById(bill_id).get();
        bill.setLabel(!bill.isLabel());
        billRepository.save(bill);
        extractAll(bill);
        return bill;
    }
}
