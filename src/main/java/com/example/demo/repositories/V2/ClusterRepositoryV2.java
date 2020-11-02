package com.example.demo.repositories.V2;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.demo.config.DynamoDBConfig;
import com.example.demo.entities.V2.ClusterV2;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ClusterRepositoryV2 {

    public ClusterRepositoryV2() {
        this.mapper = DynamoDBConfig.getMapper();
    }

    private DynamoDBMapper mapper;

    public ClusterV2 addClusterV2(ClusterV2 clusterV2) {

        mapper.save(clusterV2);
        return clusterV2;
    }

    public ClusterV2 findClusterById(String clusterId) {
        return mapper.load(ClusterV2.class, clusterId);
    }

    public String deleteCluster(ClusterV2 clusterV2) {
        mapper.delete(clusterV2);
        return "cluster removed !!";
    }

    public String editCluster(ClusterV2 clusterV2) {
        mapper.save(clusterV2, buildExpression(clusterV2));
        return "record updated ...";
    }

    private DynamoDBSaveExpression buildExpression(ClusterV2 clusterV2) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(String.valueOf(clusterV2.getId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }


}