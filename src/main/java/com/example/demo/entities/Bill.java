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

    public void setLabel(boolean lable){
        this.label = lable;
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
                        if (electricityMeanMonth.getElectricityMeanMonth1() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricityMeanMonth1());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 2:
                        if (electricityMeanMonth.getElectricityMeanMonth2() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricityMeanMonth2());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 3:
                        if (electricityMeanMonth.getElectricityMeanMonth3() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricityMeanMonth3());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 4:
                        if (electricityMeanMonth.getElectricityMeanMonth4() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricityMeanMonth4());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 5:
                        if (electricityMeanMonth.getElectricityMeanMonth5() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricityMeanMonth5());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 6:
                        if (electricityMeanMonth.getElectricityMeanMonth6() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricityMeanMonth6());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 7:
                        if (electricityMeanMonth.getElectricityMeanMonth7() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricityMeanMonth7());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 8:
                        if (electricityMeanMonth.getElectricityMeanMonth8() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricityMeanMonth8());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 9:
                        if (electricityMeanMonth.getElectricityMeanMonth9() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricityMeanMonth9());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 10:
                        if (electricityMeanMonth.getElectricityMeanMonth10() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricityMeanMonth10());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 11:
                        if (electricityMeanMonth.getElectricityMeanMonth11() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricityMeanMonth11());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                    case 12:
                        if (electricityMeanMonth.getElectricityMeanMonth12() == 0) {
                            a = Math.abs(amount - userMean.getElectricity());
                        }
                        else {
                            a = Math.abs(amount - electricityMeanMonth.getElectricityMeanMonth12());
                        }
                        b = userStd.getElectricity() * 2;
                        break;
                }
                break;
            case "phone and internet":
                switch (month) {
                    case 1:
                        if (internetMeanMonth.getInternetMeanMonth1() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternetMeanMonth1());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 2:
                        if (internetMeanMonth.getInternetMeanMonth2() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternetMeanMonth2());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 3:
                        if (internetMeanMonth.getInternetMeanMonth3() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternetMeanMonth3());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 4:
                        if (internetMeanMonth.getInternetMeanMonth4() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternetMeanMonth4());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 5:
                        if (internetMeanMonth.getInternetMeanMonth5() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternetMeanMonth5());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 6:
                        if (internetMeanMonth.getInternetMeanMonth6() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternetMeanMonth6());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 7:
                        if (internetMeanMonth.getInternetMeanMonth7() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternetMeanMonth7());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 8:
                        if (internetMeanMonth.getInternetMeanMonth8() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternetMeanMonth8());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 9:
                        if (internetMeanMonth.getInternetMeanMonth9() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternetMeanMonth9());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 10:
                        if (internetMeanMonth.getInternetMeanMonth10() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternetMeanMonth10());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 11:
                        if (internetMeanMonth.getInternetMeanMonth11() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternetMeanMonth11());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                    case 12:
                        if (internetMeanMonth.getInternetMeanMonth12() == 0) {
                            a = Math.abs(amount - userMean.getInternet());
                        }
                        else {
                            a = Math.abs(amount - internetMeanMonth.getInternetMeanMonth12());
                        }
                        b = userStd.getInternet() * 2;
                        break;
                }
                break;
            case "gas":
                switch (month) {
                    case 1:
                        if (gasMeanMonth.getGasMeanMonth1() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGasMeanMonth1());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 2:
                        if (gasMeanMonth.getGasMeanMonth2() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGasMeanMonth2());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 3:
                        if (gasMeanMonth.getGasMeanMonth3() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGasMeanMonth3());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 4:
                        if (gasMeanMonth.getGasMeanMonth4() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGasMeanMonth4());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 5:
                        if (gasMeanMonth.getGasMeanMonth5() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGasMeanMonth5());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 6:
                        if (gasMeanMonth.getGasMeanMonth6() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGasMeanMonth6());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 7:
                        if (gasMeanMonth.getGasMeanMonth7() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGasMeanMonth7());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 8:
                        if (gasMeanMonth.getGasMeanMonth8() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGasMeanMonth8());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 9:
                        if (gasMeanMonth.getGasMeanMonth9() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGasMeanMonth9());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 10:
                        if (gasMeanMonth.getGasMeanMonth10() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGasMeanMonth10());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 11:
                        if (gasMeanMonth.getGasMeanMonth11() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGasMeanMonth11());
                        }
                        b = userStd.getGas() * 2;
                        break;
                    case 12:
                        if (gasMeanMonth.getGasMeanMonth12() == 0) {
                            a = Math.abs(amount - userMean.getGas());
                        }
                        else {
                            a = Math.abs(amount - gasMeanMonth.getGasMeanMonth12());
                        }
                        b = userStd.getGas() * 2;
                        break;
                }
                break;
            case "water":
                switch (month) {
                    case 1:
                        if (waterMeanMonth.getWaterMeanMonth1() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWaterMeanMonth1());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 2:
                        if (waterMeanMonth.getWaterMeanMonth2() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWaterMeanMonth2());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 3:
                        if (waterMeanMonth.getWaterMeanMonth3() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWaterMeanMonth3());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 4:
                        if (waterMeanMonth.getWaterMeanMonth4() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWaterMeanMonth4());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 5:
                        if (waterMeanMonth.getWaterMeanMonth5() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWaterMeanMonth5());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 6:
                        if (waterMeanMonth.getWaterMeanMonth6() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWaterMeanMonth6());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 7:
                        if (waterMeanMonth.getWaterMeanMonth7() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWaterMeanMonth7());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 8:
                        if (waterMeanMonth.getWaterMeanMonth8() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWaterMeanMonth8());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 9:
                        if (waterMeanMonth.getWaterMeanMonth9() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWaterMeanMonth9());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 10:
                        if (waterMeanMonth.getWaterMeanMonth10() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWaterMeanMonth10());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 11:
                        if (waterMeanMonth.getWaterMeanMonth11() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWaterMeanMonth11());
                        }
                        b = userStd.getWater() * 2;
                        break;
                    case 12:
                        if (waterMeanMonth.getWaterMeanMonth12() == 0) {
                            a = Math.abs(amount - userMean.getWater());
                        }
                        else {
                            a = Math.abs(amount - waterMeanMonth.getWaterMeanMonth12());
                        }
                        b = userStd.getWater() * 2;
                        break;
                }
                break;
        }
        this.label = a < b;
    }
}
