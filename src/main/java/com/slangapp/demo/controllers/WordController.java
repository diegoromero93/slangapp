package com.slangapp.demo.controllers;

import com.slangapp.demo.controllers.request.WordRequest;
import com.slangapp.demo.models.Word;
import com.slangapp.demo.repositories.WordRepository;
import com.slangapp.demo.controllers.responses.WordResponse;
import com.slangapp.demo.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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


    @GetMapping(value = {"", "/", "/list"})
    public ResponseEntity<?> index(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
                            @RequestParam(defaultValue = "10", required = false) Integer itemsPerPage,
                            @RequestParam(defaultValue = "id", required = false) String[] sortBy,
                            @RequestParam(defaultValue = "false",required = false) String[] desc,
                            @RequestParam(defaultValue = "", required = false) String searchName) {

        Page<Word> wordPage = wordService.getAllWords(pageNo, itemsPerPage, sortBy, desc, searchName);
        return new ResponseEntity<>(wordPage, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping("/{word_id}")
    public ResponseEntity<?> getWord(@PathVariable("word_id") long wordId){
        Word word = null;
        if(wordRepository.existsById(wordId)){
            word = wordRepository.findById(wordId).orElse(new Word());
        }

        return new ResponseEntity<>("", new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public WordResponse createWord(@Valid @RequestBody WordRequest wordRequest) throws InterruptedException, ExecutionException, IOException {
        return wordService.save(wordRequest);
    }
}
