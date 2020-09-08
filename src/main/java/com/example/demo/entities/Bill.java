package com.example.demo.entities;


import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Entity
@Table(name = "bill")
public class Bill{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    private Date date;

    @Column(name = "amount")
    private double amount;

    @Column(name = "type")
    private String type;

    @Column(name = "number")
    private String number;

    @Column(name = "location")
    private String location;

    @Column(name = "month")
    private int month;

    @Column(name = "label")
    private boolean label;

    public Bill() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate()  {
        return date;
    }

    public void setDate(Date date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        this.date = formatter.parse(formatter.format(date));
    }

    public int getMonth() {
        return month;
    }

    public void setMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.month = calendar.get(Calendar.MONTH) + 1;
    }

    public boolean isLabel() {
        return label;
    }

    public void setLabel(){
        this.label = true;
    }

    public void setNewUserBillLabel(Cluster cluster) {
        double a = 0.0;
        double b = 0.0;
        switch (type.toLowerCase()){
            case "electricity":
                a = Math.abs(amount - cluster.getElectricityClusterMean());
                b = cluster.getElectricityClusterStd()*2;
                break;
            case "phone and internet":
                a = Math.abs(amount - cluster.getInternetClusterMean());
                b = cluster.getInternetClusterStd()*2;
                break;
            case "gas":
                a = Math.abs(amount - cluster.getGasClusterMean());
                b = cluster.getGasClusterStd()*2;
                break;
            case "water":
                a = Math.abs(amount - cluster.getWaterClusterMean());
                b = cluster.getWaterClusterStd()*2;
                break;
        }
        this.label = a < b;
    }

    public void setOldUserBillLabel(){
        UserMean userMean = user.getUserMean();
        UserStd userStd = user.getUserStd();
        ElectricityMeanMonth electricityMeanMonth = user.getElectricityMeanMonth();
        InternetMeanMonth internetMeanMonth = user.getInternetMeanMonth();
        WaterMeanMonth waterMeanMonth = user.getWaterMeanMonth();
        GasMeanMonth gasMeanMonth = user.getGasMeanMonth();
        double a = 0;
        double b = 0;
        switch (type.toLowerCase()){
            case "electricity":
                switch (month) {
                    case 1:
                        if (electricityMeanMonth.getElectricity_mm1() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricity_mm1());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 2:
                        if (electricityMeanMonth.getElectricity_mm2() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricity_mm2());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 3:
                        if (electricityMeanMonth.getElectricity_mm3() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricity_mm3());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 4:
                        if (electricityMeanMonth.getElectricity_mm4() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricity_mm4());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 5:
                        if (electricityMeanMonth.getElectricity_mm5() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricity_mm5());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 6:
                        if (electricityMeanMonth.getElectricity_mm6() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricity_mm6());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 7:
                        if (electricityMeanMonth.getElectricity_mm7() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricity_mm7());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 8:
                        if (electricityMeanMonth.getElectricity_mm8() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricity_mm8());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 9:
                        if (electricityMeanMonth.getElectricity_mm9() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricity_mm9());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 10:
                        if (electricityMeanMonth.getElectricity_mm10() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricity_mm10());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 11:
                        if (electricityMeanMonth.getElectricity_mm11() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricity_mm11());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 12:
                        if (electricityMeanMonth.getElectricity_mm12() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricity_mm12());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                }
                break;
            case "phone and internet":
                switch (month) {
                    case 1:
                        if (internetMeanMonth.getInternet_mm1() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternet_mm1());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 2:
                        if (internetMeanMonth.getInternet_mm2() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternet_mm2());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 3:
                        if (internetMeanMonth.getInternet_mm3() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternet_mm3());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 4:
                        if (internetMeanMonth.getInternet_mm4() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternet_mm4());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 5:
                        if (internetMeanMonth.getInternet_mm5() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternet_mm5());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 6:
                        if (internetMeanMonth.getInternet_mm6() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternet_mm6());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 7:
                        if (internetMeanMonth.getInternet_mm7() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternet_mm7());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 8:
                        if (internetMeanMonth.getInternet_mm8() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternet_mm8());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 9:
                        if (internetMeanMonth.getInternet_mm9() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternet_mm9());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 10:
                        if (internetMeanMonth.getInternet_mm10() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternet_mm10());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 11:
                        if (internetMeanMonth.getInternet_mm11() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternet_mm11());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 12:
                        if (internetMeanMonth.getInternet_mm12() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternet_mm12());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                }
                break;
            case "gas":
                switch (month) {
                    case 1:
                        if (gasMeanMonth.getGas_mm1() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGas_mm1());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 2:
                        if (gasMeanMonth.getGas_mm2() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGas_mm2());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 3:
                        if (gasMeanMonth.getGas_mm3() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGas_mm3());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 4:
                        if (gasMeanMonth.getGas_mm4() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGas_mm4());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 5:
                        if (gasMeanMonth.getGas_mm5() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGas_mm5());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 6:
                        if (gasMeanMonth.getGas_mm6() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGas_mm6());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 7:
                        if (gasMeanMonth.getGas_mm7() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGas_mm7());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 8:
                        if (gasMeanMonth.getGas_mm8() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGas_mm8());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 9:
                        if (gasMeanMonth.getGas_mm9() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGas_mm9());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 10:
                        if (gasMeanMonth.getGas_mm10() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGas_mm10());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 11:
                        if (gasMeanMonth.getGas_mm11() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGas_mm11());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 12:
                        if (gasMeanMonth.getGas_mm12() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGas_mm12());
                        }
                        b = userStd.getGas() * 2;
                        break;
                }
                break;
            case "water":
                switch (month) {
                    case 1:
                        if (waterMeanMonth.getWater_mm1() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWater_mm1());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 2:
                        if (waterMeanMonth.getWater_mm2() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWater_mm2());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 3:
                        if (waterMeanMonth.getWater_mm3() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWater_mm3());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 4:
                        if (waterMeanMonth.getWater_mm4() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWater_mm4());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 5:
                        if (waterMeanMonth.getWater_mm5() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWater_mm5());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 6:
                        if (waterMeanMonth.getWater_mm6() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWater_mm6());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 7:
                        if (waterMeanMonth.getWater_mm7() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWater_mm7());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 8:
                        if (waterMeanMonth.getWater_mm8() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWater_mm8());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 9:
                        if (waterMeanMonth.getWater_mm9() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWater_mm9());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 10:
                        if (waterMeanMonth.getWater_mm10() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWater_mm10());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 11:
                        if (waterMeanMonth.getWater_mm11() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWater_mm11());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 12:
                        if (waterMeanMonth.getWater_mm12() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWater_mm12());
                        }
                        b = userStd.getWater() * 2;
                        break;
                }
                break;
        }
        this.label = a < b;
    }
}
