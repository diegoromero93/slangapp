package com.slangapp.demo.services;

import com.slangapp.demo.controllers.request.WordRequest;
import com.slangapp.demo.controllers.responses.WordResponse;
import com.slangapp.demo.models.Word;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface WordService {
    void saveAll(List<Word> words) throws IOException, ExecutionException, InterruptedException;
    WordResponse findById(long id);
    WordResponse save(WordRequest word) throws IOException, ExecutionException, InterruptedException;
    Page<WordResponse> getAllWords(Integer pageNo, Integer itemsPerPage, String[] sortBy, String[] desc, String word);
}
