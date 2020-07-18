package com.slangapp.demo.services.impl;

import com.slangapp.demo.services.ReadDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ReadDictionaryServiceImpl implements ReadDictionaryService {

    @Autowired
    ResourceLoader resourceLoader;

    private static String data;

    @PostConstruct
    public void init() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/CMU-ipa-syllables.txt");
        InputStream dictionaryStream = resource.getInputStream();
        try
        {
            byte[] bdata = FileCopyUtils.copyToByteArray(dictionaryStream);
            data = new String(bdata, StandardCharsets.UTF_8);
            log.info(data);
        }
        catch (IOException e)
        {
            log.error("IOException", e);
            throw e;
        }
    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<String> getIPAPhonetic(String word) throws FileNotFoundException {
        Scanner in = null;
        String phonetic = null;
        in = new Scanner(data);
        while(in.hasNext())
        {
            String line = in.nextLine();
            String search = word.toLowerCase() + "  ";
            if(line.startsWith(search)){
                String[] split = line.split(search);
                if(split.length == 2){
                    phonetic = split[1];
                }
            }

        }
        return CompletableFuture.completedFuture(phonetic);
    }
}
