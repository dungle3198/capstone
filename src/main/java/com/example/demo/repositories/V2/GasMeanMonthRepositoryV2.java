package com.example.demo.repositories.V2;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.demo.config.DynamoDBConfig;
import com.example.demo.entities.V2.GasMeanMonthV2;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class GasMeanMonthRepositoryV2 {

    public GasMeanMonthRepositoryV2() {
        this.mapper = DynamoDBConfig.getMapper();
    }

    private DynamoDBMapper mapper;

    public GasMeanMonthV2 addGasMeanMonthV2(GasMeanMonthV2 gasMeanMonthV2) {

        mapper.save(gasMeanMonthV2);
        return gasMeanMonthV2;
    }

    public GasMeanMonthV2 findGasMeanMonthById(String gasMeanMonthId) {
        return mapper.load(GasMeanMonthV2.class, gasMeanMonthId);
    }

    public String deleteGasMeanMonth(GasMeanMonthV2 gasMeanMonthV2) {
        mapper.delete(gasMeanMonthV2);
        return "gasMeanMonth removed !!";
    }

    public String editGasMeanMonth(GasMeanMonthV2 gasMeanMonthV2) {
        mapper.save(gasMeanMonthV2, buildExpression(gasMeanMonthV2));
        return "record updated ...";
    }

    private DynamoDBSaveExpression buildExpression(GasMeanMonthV2 gasMeanMonthV2) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(String.valueOf(gasMeanMonthV2.getId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }


}