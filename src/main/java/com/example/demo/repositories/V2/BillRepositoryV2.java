package com.example.demo.repositories.V2;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.demo.config.DynamoDBConfig;
import com.example.demo.entities.V2.BillV2;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BillRepositoryV2 {

    public BillRepositoryV2() {
        this.mapper = DynamoDBConfig.mapper();
    }

    private DynamoDBMapper mapper;

    public BillV2 addBillV2(BillV2 BillV2) {

        mapper.save(BillV2);
        return BillV2;
    }

    public BillV2 findBillById(String billId) {
        return mapper.load(BillV2.class, billId);
    }

    public String deleteBill(BillV2 BillV2) {
        mapper.delete(BillV2);
        return "bill removed !!";
    }

    public String editBill(BillV2 BillV2) {
        mapper.save(BillV2, buildExpression(BillV2));
        return "record updated ...";
    }

    private DynamoDBSaveExpression buildExpression(BillV2 BillV2) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(String.valueOf(BillV2.getId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }


}