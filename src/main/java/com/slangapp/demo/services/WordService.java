package com.slangapp.demo.services;

import com.slangapp.demo.models.Word;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface WordService {
    List<Word> saveAll(List<Word> words) throws IOException, ExecutionException, InterruptedException;
    Word save(Word word) throws IOException, ExecutionException, InterruptedException;
    Page<Word> getAllWords(Integer pageNo, Integer itemsPerPage, String[] sortBy, String[] desc, String word);

}
