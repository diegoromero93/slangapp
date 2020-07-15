package com.slangapp.demo.services;

import com.slangapp.demo.models.Word;

import java.io.IOException;
import java.util.List;

public interface WordService {
    /**
     * Método para guardar
     * @param words
     * @return
     * @throws IOException
     */
    List<Word> saveAll(List<Word> words) throws IOException;
    Word save(Word word) throws IOException;
}
