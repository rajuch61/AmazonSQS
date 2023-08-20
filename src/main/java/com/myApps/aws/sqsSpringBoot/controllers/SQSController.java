package com.myApps.aws.sqsSpringBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myApps.aws.sqsSpringBoot.Model.MessageModel;

@RestController
public class SQSController {

	@Autowired
    private QueueMessagingTemplate queueMessagingTemplate;
	
	@Autowired
	private RestTemplate restTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String sqsEndPoint;
    
    @Value("${api.docNofiyUri}")
    private String apiEndPoint;

    @GetMapping("/test")
    public String test() {
    	return "Hey you got a new Message: ";
    }
    
    @PostMapping("/test")
    public String test1(@RequestBody MessageModel msg) {
    	// logic to process the response msg
    	return "Notification Processed for new Message: " + msg.getInfo();
    }
    
    @GetMapping("/put")
    public void putMessagedToQueue(@RequestBody Message message) {
        queueMessagingTemplate.send(sqsEndPoint, MessageBuilder.withPayload(message).build());
    }

    @SqsListener("SQS-queue")
    public void loadMessagesFromQueue(String message) throws JsonMappingException, JsonProcessingException {
    	MessageModel msg = new ObjectMapper().readValue(message, MessageModel.class); 
    	System.out.println(msg.getStatusCode());
    	if(msg.getStatusCode().equals("Success")) {
    		String res = restTemplate.postForObject(apiEndPoint, msg, String.class);
    		System.out.println(res);
    	} else {
    		// Logic to filter the error messages
    	}
        	
        
    }
}
