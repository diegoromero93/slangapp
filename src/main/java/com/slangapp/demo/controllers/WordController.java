package com.slangapp.demo.controllers;

import com.slangapp.demo.config.exceptions.MissingHeaderAuthException;
import com.slangapp.demo.controllers.request.WordRequest;
import com.slangapp.demo.controllers.responses.WordResponse;
import com.slangapp.demo.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

    @Value("${aws.header.validation}")
    String validationHeaderProp;


    @GetMapping(value = {"", "/", "/list"})
    public Page<WordResponse> index(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
                            @RequestParam(defaultValue = "10", required = false) Integer itemsPerPage,
                            @RequestParam(defaultValue = "id", required = false) String[] sortBy,
                            @RequestParam(defaultValue = "false",required = false) String[] desc,
                            @RequestParam(defaultValue = "", required = false) String searchName) {
        return wordService.getAllWords(pageNo, itemsPerPage, sortBy, desc, searchName);
    }


    @GetMapping("/{word_id}")
    public WordResponse getWord(@PathVariable("word_id") long wordId){
        return wordService.findById(wordId);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public WordResponse createWord(@Valid @RequestBody WordRequest wordRequest, @RequestHeader(name="validation-header")  String validationHeader) throws InterruptedException, ExecutionException, IOException {
        if(validationHeaderProp.equals(validationHeader)){
            return wordService.save(wordRequest);
        } else {
             throw new MissingHeaderAuthException("Mission validation-header");
        }
    }
}
