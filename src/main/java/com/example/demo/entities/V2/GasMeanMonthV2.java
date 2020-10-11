package com.example.demo.entities.V2;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.io.Serializable;


@DynamoDBTable(tableName = "gas_mean_month")
public class GasMeanMonthV2 implements Serializable {
    public GasMeanMonthV2(String id) {
        this.id = id;

    }


    @DynamoDBHashKey  (attributeName = "id")
    public String getId()
    {
        return id;
    }
    @DynamoDBAutoGeneratedKey
    public String id;

    @DynamoDBAttribute (attributeName="gas_mm1")
    public double getGas_mm1()
    {
        return this.gas_mm1;
    }
    public double gas_mm1;

    @DynamoDBAttribute (attributeName="gas_mm2")
    public double getGas_mm2()
    {
        return this.gas_mm2;
    }
    public double gas_mm2;

    @DynamoDBAttribute (attributeName="gas_mm3")
    public double getGas_mm3()
    {
        return this.gas_mm3;
    }
    public double gas_mm3;

    @DynamoDBAttribute (attributeName="gas_mm4")
    public double getGas_mm4()
    {
        return this.gas_mm4;
    }
    public double gas_mm4;

    @DynamoDBAttribute (attributeName="gas_mm5")
    public double getGas_mm5()
    {
        return this.gas_mm5;
    }
    public double gas_mm5;

    @DynamoDBAttribute (attributeName="gas_mm6")
    public double getGas_mm6()
    {
        return this.gas_mm6;
    }
    public double gas_mm6;

    @DynamoDBAttribute (attributeName="gas_mm7")
    public double getGas_mm7()
    {
        return this.gas_mm7;
    }
    public double gas_mm7;

    @DynamoDBAttribute (attributeName="gas_mm8")
    public double getGas_mm8()
    {
        return this.gas_mm8;
    }
    public double gas_mm8;

    @DynamoDBAttribute (attributeName="gas_mm9")
    public double getGas_mm9()
    {
        return this.gas_mm9;
    }
    public double gas_mm9;

    @DynamoDBAttribute (attributeName="gas_mm10")
    public double getGas_mm10()
    {
        return this.gas_mm10;
    }
    public double gas_mm10;

    @DynamoDBAttribute (attributeName="gas_mm11")
    public double getGas_mm11()
    {
        return this.gas_mm11;
    }
    public double gas_mm11;

    @DynamoDBAttribute (attributeName="gas_mm12")
    public double getGas_mm12()
    {
        return this.gas_mm12;
    }
    public double gas_mm12;

    @DynamoDBAttribute (attributeName="user_id")
    public String getUserId()
    {
        return this.user_id;
    }
    public String user_id;
}