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
    public static final String ACCESS_KEY = "AKIATZE7GBKOAEUM22EG";
    public static final String SECRET_KEY = "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";

    @Bean
    public DynamoDBMapper mapper() {
        return new DynamoDBMapper(amazonDynamoDBConfig());
    }

    private AmazonDynamoDB amazonDynamoDBConfig() {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(SERVICE_ENDPOINT, REGION))
                .withCredentials(new AWSStaticCredentialsProvider( new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY)))
                .build();
    }
}
