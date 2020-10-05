package com.example.demo.repositories;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import  com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryV2 {

    @Autowired
    private DynamoDBMapper mapper;


    public User addUser(User user) {
        mapper.save(user);
        return user;
    }

    public User findUserByUserId(String userId) {
        return mapper.load(User.class, userId);
    }

    public String deleteUser(User user) {
        mapper.delete(user);
        return "user removed !!";
    }

    public String editUser(User user) {
        mapper.save(user, buildExpression(user));
        return "record updated ...";
    }

    private DynamoDBSaveExpression buildExpression(User user) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(String.valueOf(user.getId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }


}