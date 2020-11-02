package com.example.demo.repositories.V2;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.demo.config.DynamoDBConfig;
import com.example.demo.entities.V2.UserStdV2;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserStdRepositoryV2 {

    public UserStdRepositoryV2() {
        this.mapper = DynamoDBConfig.getMapper();
    }

    private DynamoDBMapper mapper;

    public UserStdV2 addUserStdV2(UserStdV2 userStdV2) {

        mapper.save(userStdV2);
        return userStdV2;
    }

    public UserStdV2 findUserStdById(String userId) {
        return mapper.load(UserStdV2.class, userId);
    }

    public String deleteUserStd(UserStdV2 userStdV2) {
        mapper.delete(userStdV2);
        return "user removed !!";
    }

    public String editUserStd(UserStdV2 userStdV2) {
        mapper.save(userStdV2, buildExpression(userStdV2));
        return "record updated ...";
    }

    private DynamoDBSaveExpression buildExpression(UserStdV2 userStdV2) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(String.valueOf(userStdV2.getId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }


}