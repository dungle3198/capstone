package com.example.demo.controllers;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.example.demo.config.DynamoDBConfig;
import com.example.demo.repositories.LogRepository;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.example.demo.entities.*;
import com.example.demo.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogController {
    private final LogRepository logRepository;
    @Autowired
    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

}
