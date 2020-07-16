package com.slangapp.demo.controllers;

import com.slangapp.demo.enums.ResourceTypeEnum;
import com.slangapp.demo.models.Resource;
import com.slangapp.demo.repositories.ResourceRepository;
import com.slangapp.demo.responses.WordScrambleActivity;
import com.slangapp.demo.models.Word;
import com.slangapp.demo.repositories.WordRepository;
import com.slangapp.demo.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/activity")
public class ActivityController {

    @Autowired
    WordRepository wordRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    WordService wordService;



    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<?> getWord() throws IOException, ExecutionException, InterruptedException {

        wordService.save(new Word());

        Word word = wordRepository.findById(1l).get();
        List<Resource> resourceList = resourceRepository.findAllByResourceTypeAndWord(ResourceTypeEnum.AUDIO.getCode(),word);
        WordScrambleActivity wordScrambleActivity = new WordScrambleActivity();
        wordScrambleActivity.setLetters(word.getWord());
        wordScrambleActivity.setResources(resourceList);
        return new ResponseEntity<WordScrambleActivity>(wordScrambleActivity, new HttpHeaders(), HttpStatus.OK);
    }
}
