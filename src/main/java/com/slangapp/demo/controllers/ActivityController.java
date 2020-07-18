package com.slangapp.demo.controllers;

import com.slangapp.demo.controllers.request.ActivityEvaluationRequest;
import com.slangapp.demo.controllers.responses.ActivityResponse;
import com.slangapp.demo.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @GetMapping(value = {"", "/"})
    public ActivityResponse initial() {
        return activityService.getUserInitialActivity();
    }

    @GetMapping("/{id}")
    public ActivityResponse getCurrentActivity(@PathVariable("id") Long id) {
        return  activityService.getUserActivity(id);
    }

    @PostMapping("/{id}/evaluate")
    public ActivityResponse evaluate(@PathVariable("id") Long id, @Valid @RequestBody ActivityEvaluationRequest activityEvaluationRequest){
        return  activityService.evaluate(activityEvaluationRequest, id);
    }
}
