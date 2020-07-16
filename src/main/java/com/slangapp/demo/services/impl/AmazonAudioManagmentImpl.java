package com.slangapp.demo.services.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.slangapp.demo.services.AmazonAudioManagment;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.concurrent.CompletableFuture;

@Service
public class AmazonAudioManagmentImpl implements AmazonAudioManagment {

    @Value("${aws.api.accessKey}")
    String accessKey;

    @Value("${aws.api.secretKey}")
    String secretKey;

    @Value("${aws.s3.url}")
    String s3Url;

    @Value("${aws.s3.bucketName}")
    String bucketName;

    private static final String FILE_TO = "/tmp/temp.mp3";
    private static final String VOICE = "Salli";
    private static final Region REGION = Region.getRegion(Regions.US_EAST_2);
    private static final SecureRandom random = new SecureRandom();

    private AmazonPollyClient polly;
    private AmazonS3 s3;

    @PostConstruct
    public void init(){
        AWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        polly = new AmazonPollyClient(awsCreds, new ClientConfiguration());
        polly.setRegion(REGION);
        s3 = AmazonS3ClientBuilder.standard().
                withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.US_EAST_2).build();
    }


    @Override
    @Async("taskExecutor")
    public CompletableFuture<String> createAndSaveAudioFile(String word) throws IOException {
        SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest().withText(word).withVoiceId(VOICE)
                .withOutputFormat(OutputFormat.Mp3);
        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);
        InputStream speechStream = synthRes.getAudioStream();
        File tempFile = new File(FILE_TO);
        FileUtils.copyInputStreamToFile(speechStream, tempFile);
        String s3FileName = generateRandomString() + "/" +word + ".mp3";
        try {
            s3.putObject(
                    new PutObjectRequest(bucketName, s3FileName, tempFile)
                            .withCannedAcl(CannedAccessControlList.AuthenticatedRead.PublicRead)
            );
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
            throw e;
        }

        return CompletableFuture.completedFuture(s3Url + s3FileName);
    }


    private String generateRandomString(){
        long n = random.nextLong();
        if (n == Long.MIN_VALUE) {
            n = 0;
        } else {
            n = Math.abs(n);
        }
        return Long.toString(n);
    }

}
