package com.slangapp.demo.services.impl;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
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
import com.slangapp.demo.enums.ResourceType;
import com.slangapp.demo.models.Resource;
import com.slangapp.demo.models.Word;
import com.slangapp.demo.repositories.ResourceRepository;
import com.slangapp.demo.repositories.WordRepository;
import com.slangapp.demo.services.WordService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {

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

    @Autowired
    WordRepository wordRepository;

    @Autowired
    ResourceRepository resourceRepository;


    @Override
    public List<Word> saveAll(List<Word> words) throws IOException {
        List<Resource> resourceList = new ArrayList<>();
        for(Word word: words){
            String resourceURL = createAudioFile(word.getWord());
            Resource resource = new Resource();
            resource.setWord(word);
            resource.setResourceType(ResourceType.AUDIO);
            resource.setResourceUrl(resourceURL);
            resourceList.add(resource);
        }
        List<Word> wordList = wordRepository.saveAll(words);
        resourceRepository.saveAll(resourceList);
        return wordList;
    }

    @Override
    public Word save(Word word) throws IOException {
        String resourceURL = createAudioFile(word.getWord());
        Resource resource = new Resource();
        resource.setWord(word);
        resource.setResourceType(ResourceType.AUDIO);
        resource.setResourceUrl(resourceURL);
        Word newWord = wordRepository.save(word);
        resourceRepository.save(resource);
        return newWord;

    }


    @PostConstruct
    public void init(){
        AWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        polly = new AmazonPollyClient(awsCreds, new ClientConfiguration());
        polly.setRegion(REGION);
        s3 = AmazonS3ClientBuilder.standard().
                withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.US_EAST_2).build();
    }


    private String generateRandomString(){
        long n = random.nextLong();
        if (n == Long.MIN_VALUE) {
            n = 0;      // corner case
        } else {
            n = Math.abs(n);
        }
        return Long.toString(n);

    }


    private String createAudioFile(String word) throws IOException {
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

        return s3Url + s3FileName;
    }
}
