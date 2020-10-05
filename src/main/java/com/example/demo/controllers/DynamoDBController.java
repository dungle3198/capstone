package com.example.demo.controllers;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.example.demo.entities.User;

import java.util.HashMap;
import java.util.Map;

public class DynamoDBController {
    public AmazonDynamoDB client;
    DynamoDB dynamoDB;
    Table userTable;

    public DynamoDBController() {
        client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("dynamodb.ap-southeast-1.amazonaws.com", "ap-southeast-1"))
                .withCredentials(new AWSStaticCredentialsProvider( new BasicAWSCredentials("AKIATZE7GBKOAEUM22EG","8vbnKI2S6eYf1QqOzvct9oAg2oKHGi13iGLwqFcl")))
                .build();
        dynamoDB = new DynamoDB(client);

    }

}
