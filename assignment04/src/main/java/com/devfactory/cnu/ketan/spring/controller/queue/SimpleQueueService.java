package com.devfactory.cnu.ketan.spring.controller.queue;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

/**
 * Created by ketanpatil on 11/07/16.
 */
public class SimpleQueueService {
    private static AmazonSQS sqs;
    public SimpleQueueService() {


        AWSCredentials credentials = null;
        try {

        } catch (Exception e) {
            //TODO: cannot find credentials, throw exception
        }

        AmazonSQS sqs = new AmazonSQSClient(new DefaultAWSCredentialsProviderChain());
        Region usEast1 = Region.getRegion(Regions.US_EAST_1);
        sqs.setRegion(usEast1);

        this.sqs = sqs;
    }

    public void sendMessage(String msg) {
        try {
            String myQueueUrl = sqs.getQueueUrl("cnu2016_ketan_patil_log_queue").getQueueUrl();
            //String msg = "This is my message text.";
            sqs.sendMessage(new SendMessageRequest(myQueueUrl, msg));

        } catch (AmazonServiceException ase) {
            //TODO: catch exception
        } catch (AmazonClientException ace) {
            //TODO: catch exception
        }
    }


}
