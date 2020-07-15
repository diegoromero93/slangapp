package com.slangapp.demo.controllers;

import com.slangapp.demo.enums.ResourceType;
import com.slangapp.demo.models.Resource;
import com.slangapp.demo.repositories.ResourceRepository;
import com.slangapp.demo.responses.WordScrambleActivity;
import com.slangapp.demo.models.Word;
import com.slangapp.demo.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/word")
public class WordController {

    @Autowired
    WordRepository wordRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<?> getWord() throws IOException {
        Word word = wordRepository.findById(4l).get();
        List<Resource> resourceList = resourceRepository.findAllByResourceTypeAndWord(ResourceType.AUDIO.getCode(),word);
        WordScrambleActivity wordScrambleActivity = new WordScrambleActivity();
        wordScrambleActivity.setLetters(word.getWord());
        wordScrambleActivity.setResources(resourceList);
        return new ResponseEntity<WordScrambleActivity>(wordScrambleActivity, new HttpHeaders(), HttpStatus.OK);
    }
}
