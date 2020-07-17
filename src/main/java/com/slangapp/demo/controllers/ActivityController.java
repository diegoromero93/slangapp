package com.slangapp.demo.controllers;

import com.slangapp.demo.repositories.ResourceRepository;
import com.slangapp.demo.repositories.WordRepository;
import com.slangapp.demo.controllers.responses.ActivityResponse;
import com.slangapp.demo.services.ActivityService;
import com.slangapp.demo.services.WordService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/activity")
public class ActivityController {

    @NonNull
    private final WordRepository wordRepository;

    @NonNull
    private final ResourceRepository resourceRepository;

    @NonNull
    private final WordService wordService;

    @NonNull
    private final ActivityService activityService;



    @GetMapping("/{id}")
    public ResponseEntity<?> getWord(@PathVariable("id") Long id) {
        ActivityResponse nextActivity = activityService.getUserActivity(id);
        return new ResponseEntity<>(nextActivity, new HttpHeaders(), HttpStatus.OK);
    }
}
