package com.example.demo.repositories.V2;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.demo.config.DynamoDBConfig;
import com.example.demo.entities.V2.WaterMeanMonthV2;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WaterMeanMonthRepositoryV2 {

    public WaterMeanMonthRepositoryV2() {
        this.mapper = DynamoDBConfig.getMapper();
    }

    private DynamoDBMapper mapper;

    public WaterMeanMonthV2 addWaterMeanMonthV2(WaterMeanMonthV2 waterMeanMonthV2) {

        mapper.save(waterMeanMonthV2);
        return waterMeanMonthV2;
    }

    public WaterMeanMonthV2 findWaterMeanMonthById(String waterMeanMonthId) {
        return mapper.load(WaterMeanMonthV2.class, waterMeanMonthId);
    }

    public String deleteWaterMeanMonth(WaterMeanMonthV2 waterMeanMonthV2) {
        mapper.delete(waterMeanMonthV2);
        return "waterMeanMonth removed !!";
    }

    public String editWaterMeanMonth(WaterMeanMonthV2 waterMeanMonthV2) {
        mapper.save(waterMeanMonthV2, buildExpression(waterMeanMonthV2));
        return "record updated ...";
    }

    private DynamoDBSaveExpression buildExpression(WaterMeanMonthV2 waterMeanMonthV2) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(String.valueOf(waterMeanMonthV2.getId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }


}