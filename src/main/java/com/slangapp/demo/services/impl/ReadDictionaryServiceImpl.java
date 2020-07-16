package com.slangapp.demo.services.impl;

import com.slangapp.demo.services.ReadDictionaryService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@Service
public class ReadDictionaryServiceImpl implements ReadDictionaryService {

    private static File dictionaryFile;

    @PostConstruct
    public void init() throws IOException {
        dictionaryFile = new ClassPathResource("static/CMU-ipa-syllables.txt").getFile();
    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<String> getIPAPhonetic(String word) throws FileNotFoundException {
        Scanner in = null;
        String phonetic = null;
        in = new Scanner(dictionaryFile);
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
