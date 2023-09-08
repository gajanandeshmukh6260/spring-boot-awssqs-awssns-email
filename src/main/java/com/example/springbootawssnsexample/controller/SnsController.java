package com.example.springbootawssnsexample.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SnsController {

    // sqs arn
    String ARN_SNS_TOPIC = "arn:aws:sns:ap-south-1:182850446924:employee-service-sns";
    String ARN_SQS_TOPIC = "arn:aws:sqs:ap-south-1:182850446924:employee-service-sqs";
    private static final String SUBJECT_SQS_DEMO="SQS Demo";
    private static final String SUBJECT_EMAIL_DEMO="Email Demo";

    @Autowired
    private AmazonSNSClient amazonSNSClient;
    // send sms from sns to sqs queue

    @GetMapping("/subscribe/sqs")
    public String subcribeToSQS() {
        SubscribeRequest subscribeRequest =
                new SubscribeRequest(ARN_SNS_TOPIC, "sqs",
                        ARN_SQS_TOPIC);
        amazonSNSClient.subscribe(subscribeRequest);
        return "SQS subscription successfully";

    }


    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable(value = "message") String message) {
        String subject = getSubject(SUBJECT_SQS_DEMO);
        PublishRequest publishRequest = new PublishRequest(ARN_SNS_TOPIC, message, subject);
        amazonSNSClient.publish(publishRequest);
        return "message publish successfully...!";
    }

    private String getSubject(String string){
        return (string.equalsIgnoreCase(SUBJECT_SQS_DEMO) ? SUBJECT_SQS_DEMO : SUBJECT_EMAIL_DEMO);
    }

    // send message to email

    @GetMapping("/subscribe/{email}")
    public String subcribeToEmail(@PathVariable(value = "email") String email) {
        SubscribeRequest subscribeRequest =
                new SubscribeRequest(ARN_SNS_TOPIC, "email",
                        email);
        amazonSNSClient.subscribe(subscribeRequest);
        return "Email subscription successfully";

    }

}
