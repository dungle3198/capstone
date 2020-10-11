package com.example.demo.entities.V2;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.io.Serializable;


@DynamoDBTable(tableName = "user_std")
public class ClusterV2 implements Serializable {
    public ClusterV2(String id) {
        this.id = id;

    }


    @DynamoDBHashKey  (attributeName = "id")
    public String getId()
    {
        return id;
    }
    @DynamoDBAutoGeneratedKey
    public String id;

    @DynamoDBAttribute (attributeName="electricity_cluster_mean")
    public double getElectricity_cluster_mean()
    {
        return this.electricity_cluster_mean;
    }
    public double electricity_cluster_mean;

    @DynamoDBAttribute (attributeName="internet_cluster_mean")
    public double getInternet_cluster_mean()
    {
        return this.internet_cluster_mean;
    }
    public double internet_cluster_mean;

    @DynamoDBAttribute (attributeName="water_cluster_mean")
    public double getWater_cluster_mean()
    {
        return this.water_cluster_mean;
    }
    public double water_cluster_mean;

    @DynamoDBAttribute (attributeName="gas_cluster_mean")
    public double getGas_cluster_mean()
    {
        return this.gas_cluster_mean;
    }
    public double gas_cluster_mean;

    @DynamoDBAttribute (attributeName="electricity_cluster_std")
    public double getElectricity_cluster_std()
    {
        return this.electricity_cluster_std;
    }
    public double electricity_cluster_std;

    @DynamoDBAttribute (attributeName="internet_cluster_std")
    public double getInternet_cluster_std()
    {
        return this.internet_cluster_std;
    }
    public double internet_cluster_std;

    @DynamoDBAttribute (attributeName="water_cluster_std")
    public double getWater_cluster_std()
    {
        return this.water_cluster_std;
    }
    public double water_cluster_std;

    @DynamoDBAttribute (attributeName="gas_cluster_std")
    public double getGas_cluster_std()
    {
        return this.gas_cluster_std;
    }
    public double gas_cluster_std;


}