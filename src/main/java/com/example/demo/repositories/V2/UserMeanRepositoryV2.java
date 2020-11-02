package com.example.demo.repositories.V2;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.demo.config.DynamoDBConfig;
import com.example.demo.entities.V2.UserMeanV2;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserMeanRepositoryV2 {

    public UserMeanRepositoryV2() {
        this.mapper = DynamoDBConfig.getMapper();
    }

    private DynamoDBMapper mapper;

    public UserMeanV2 addUserMeanV2(UserMeanV2 userMeanV2) {

        mapper.save(userMeanV2);
        return userMeanV2;
    }

    public UserMeanV2 findUserMeanById(String userId) {
        return mapper.load(UserMeanV2.class, userId);
    }

    public String deleteUserStd(UserMeanV2 userMeanV2) {
        mapper.delete(userMeanV2);
        return "userMean removed !!";
    }

    public String editUserStd(UserMeanV2 userMeanV2) {
        mapper.save(userMeanV2, buildExpression(userMeanV2));
        return "record updated ...";
    }

    private DynamoDBSaveExpression buildExpression(UserMeanV2 userMeanV2) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(String.valueOf(userMeanV2.getId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }


}