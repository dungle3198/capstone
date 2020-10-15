package com.example.demo.config;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    public static final String SERVICE_ENDPOINT = "dynamodb.ap-southeast-1.amazonaws.com";
    public static final String REGION = "ap-southeast-1";
    public static final String ACCESS_KEY = "AKIATZE7GBKOI4IPBPUH";
    public static final String SECRET_KEY = "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";

    public static DynamoDBMapper mapper() {
        return new DynamoDBMapper(amazonDynamoDBConfig());
    }

    public static AmazonDynamoDB amazonDynamoDBConfig() {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(SERVICE_ENDPOINT, REGION))
                .withCredentials(new AWSStaticCredentialsProvider( new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY)))
                .build();
    }
}
