package com.slangapp.demo.controllers;

import com.slangapp.demo.models.Word;
import com.slangapp.demo.repositories.WordRepository;
import com.slangapp.demo.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/word")
public class WordController {

    @Autowired
    WordService wordService;

    @Autowired
    WordRepository wordRepository;



    @GetMapping("/{wordId}")
    public ResponseEntity<?> getWord(@PathVariable("wordId") long wordId){
        Word word = null;
        if(wordRepository.existsById(wordId)){
            word = wordRepository.findById(wordId).orElse(new Word());
            word.getResources();
        }
        return new ResponseEntity<>(word, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> createWord(@RequestParam String word) throws InterruptedException, ExecutionException, IOException {
        Word createdWord = new Word();
        createdWord.setWord(word);
        wordService.save(createdWord);
        return new ResponseEntity<>(createdWord, new HttpHeaders(), HttpStatus.OK);
    }
}
