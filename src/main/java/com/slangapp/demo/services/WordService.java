package com.slangapp.demo.services;

import com.slangapp.demo.models.Word;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface WordService {
    List<Word> saveAll(List<Word> words) throws IOException, ExecutionException, InterruptedException;
    Word save(Word word) throws IOException, ExecutionException, InterruptedException;
}
