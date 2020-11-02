package com.example.demo.repositories.V2;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.demo.config.DynamoDBConfig;
import com.example.demo.entities.V2.ElectricityMeanMonthV2;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ElectricityMeanMonthRepositoryV2 {

    public ElectricityMeanMonthRepositoryV2() {
        this.mapper = DynamoDBConfig.getMapper();
    }

    private DynamoDBMapper mapper;

    public ElectricityMeanMonthV2 addElectricityMeanMonthV2(ElectricityMeanMonthV2 electricityMeanMonthV2) {

        mapper.save(electricityMeanMonthV2);
        return electricityMeanMonthV2;
    }

    public ElectricityMeanMonthV2 findElectricityMeanMonthById(String electricityMeanMonthId) {
        return mapper.load(ElectricityMeanMonthV2.class, electricityMeanMonthId);
    }

    public String deleteElectricityMeanMonth(ElectricityMeanMonthV2 electricityMeanMonthV2) {
        mapper.delete(electricityMeanMonthV2);
        return "electricityMeanMonth removed !!";
    }

    public String editElectricityMeanMonth(ElectricityMeanMonthV2 electricityMeanMonthV2) {
        mapper.save(electricityMeanMonthV2, buildExpression(electricityMeanMonthV2));
        return "record updated ...";
    }

    private DynamoDBSaveExpression buildExpression(ElectricityMeanMonthV2 electricityMeanMonthV2) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(String.valueOf(electricityMeanMonthV2.getId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }


}