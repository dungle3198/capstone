package com.example.demo.controllers;

import java.text.ParseException;
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

    public void extractMeanMonth (Bill bill, List<Double> amounts){
        User user = bill.getUser();
        GasMeanMonth gasMeanMonth = user.getGasMeanMonth();
        InternetMeanMonth internetMeanMonth = user.getInternetMeanMonth();
        ElectricityMeanMonth electricityMeanMonth = user.getElectricityMeanMonth();
        WaterMeanMonth waterMeanMonth = user.getWaterMeanMonth();
        user.setMeanMonthType(amounts, bill.getType(), bill.getMonth());
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
        userMean.calculateMean(amounts, bill.getType());
        userStd.calculateStd(amounts, bill.getType());
        userMeanController.editMean(userMean, userMean.getId());
        userStdController.editStd(userStd, userStd.getId());
    }

    public void extractAll(Bill bill){
        List<Double> amount_list1 = getBillAmountByUserIdAndType(bill.getUser().getId(), bill.getType());
        List<Double> amount_list2 = getBillAmountByUserIdAndTypeAndMonth(bill.getUser().getId(), bill.getType(), bill.getMonth());
        extract(bill, amount_list1);
        extractMeanMonth(bill, amount_list2);
    }

    public void update(Bill bill, boolean option){
        User user = bill.getUser();
        List<Double> amount_list1 = getBillAmountByUserIdAndType(user.getId(), bill.getType());
        List<Double> amount_list2 = getBillAmountByUserIdAndTypeAndMonth(user.getId(), bill.getType(), bill.getMonth());
        List<Integer> bill_ids1 = getBillIdByUserIdAndType(user.getId(), bill.getType());
        List<Integer> bill_ids2 = getBillIdByUserIdAndTypeAndMonth(user.getId(), bill.getType(), bill.getMonth());
        int index1 = bill_ids1.indexOf(bill.getId());
        int index2 = bill_ids2.indexOf(bill.getId());
        if (option)
        {
            amount_list1.remove(index1);
            amount_list2.remove(index2);
            if (amount_list1.isEmpty()){
                amount_list1.add(0.0);
            }
            if (amount_list2.isEmpty()){
                amount_list2.add(0.0);
            }
            extract(bill, amount_list1);
        }
        else {
            amount_list2.remove(index2);
            if (amount_list2.isEmpty()){
                amount_list2.add(0.0);
            }
        }
        extractMeanMonth(bill, amount_list2);
    }


    public void labelNewUserBill (Bill bill){
        List<Cluster> clusters = clusterController.clusters();
        double shortestDistance = 99999;
        Cluster chosenCluster = null;
        for (Cluster cluster: clusters){
            double distance = cluster.getDistance(bill.getUser().getUserMean());
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
        if (user.isNewUser()){
            labelNewUserBill(newBill);
        }
        else {newBill.setOldUserBillLabel();}
        //newBill.setLabel();
        billRepository.save(newBill);
        user.setTotal_bill(user.getBills().size());
        extractAll(newBill);
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
            bill.setMonth();
            if (!bill.getType().equalsIgnoreCase(existedBill.getType()) ||
                    bill.getAmount() != existedBill.getAmount() ||
                    bill.getUser().getId() != existedBill.getUser().getId())
            {
                update(existedBill, true);
            }
            else if (bill.getMonth() != existedBill.getMonth())
            {
                update(existedBill, false);
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
            billRepository.save(existedBill);
            extractAll(existedBill);
        }
    }

    @CrossOrigin
    @DeleteMapping("/bills/{id}")
    public void delete (@PathVariable ("id") final Integer id)
    {
        Bill bill = billRepository.findById(id).get();
        User user = bill.getUser();
        update(bill, true);
        billRepository.deleteById(id);
        user.setTotal_bill(user.getBills().size());
        userRepository.save(user);
    }

    @CrossOrigin
    @GetMapping("/bills/confirm/{id}")
    public Bill confirmBill(@PathVariable("id") final int bill_id){
        Bill bill = billRepository.findById(bill_id).get();
        bill.setLabel();
        billRepository.save(bill);
        extractAll(bill);
        return bill;
    }
}
