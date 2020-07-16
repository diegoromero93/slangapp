package com.slangapp.demo.controllers;

import com.slangapp.demo.pojos.Activity;
import com.slangapp.demo.repositories.ResourceRepository;
import com.slangapp.demo.repositories.WordRepository;
import com.slangapp.demo.services.ActivityService;
import com.slangapp.demo.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @Autowired
    ActivityService activityService;



    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<?> getWord() {
        Activity activity = activityService.getUserActivity(null);
        return new ResponseEntity<>(activity, new HttpHeaders(), HttpStatus.OK);
    }
}
