package com.example.demo.repositories.V2;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.demo.config.DynamoDBConfig;
import com.example.demo.entities.V2.UserV2;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryV2 {

    public UserRepositoryV2() {
        this.mapper = DynamoDBConfig.getMapper();
    }

    private DynamoDBMapper mapper;

    public UserV2 addUser(UserV2 user) {

        mapper.save(user);
        return user;
    }

    public UserV2 findUserByUserId(String userId) {
        return mapper.load(UserV2.class, userId);
    }

    public String deleteUser(UserV2 user) {
        mapper.delete(user);
        return "user removed !!";
    }

    public String editUser(UserV2 user) {
        mapper.save(user, buildExpression(user));
        return "record updated ...";
    }

    private DynamoDBSaveExpression buildExpression(UserV2 user) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(String.valueOf(user.getId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }


}