package com.example.demo.repositories.V2;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.demo.config.DynamoDBConfig;
import com.example.demo.entities.V2.InternetMeanMonthV2;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InternetMeanMonthRepositoryV2 {

    public InternetMeanMonthRepositoryV2() {
        this.mapper = DynamoDBConfig.mapper();
    }

    private DynamoDBMapper mapper;

    public InternetMeanMonthV2 addInternetMeanMonthV2(InternetMeanMonthV2 internetMeanMonthV2) {

        mapper.save(internetMeanMonthV2);
        return internetMeanMonthV2;
    }

    public InternetMeanMonthV2 findInternetMeanMonthById(String internetMeanMonthId) {
        return mapper.load(InternetMeanMonthV2.class, internetMeanMonthId);
    }

    public String deleteInternetMeanMonth(InternetMeanMonthV2 internetMeanMonthV2) {
        mapper.delete(internetMeanMonthV2);
        return "internetMeanMonth removed !!";
    }

    public String editInternetMeanMonth(InternetMeanMonthV2 internetMeanMonthV2) {
        mapper.save(internetMeanMonthV2, buildExpression(internetMeanMonthV2));
        return "record updated ...";
    }

    private DynamoDBSaveExpression buildExpression(InternetMeanMonthV2 internetMeanMonthV2) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(String.valueOf(internetMeanMonthV2.getId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }


}