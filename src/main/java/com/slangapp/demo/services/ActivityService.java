package com.slangapp.demo.services;

import com.slangapp.demo.config.exceptions.MissingAnswerException;
import com.slangapp.demo.controllers.request.ActivityEvaluationRequest;
import com.slangapp.demo.controllers.responses.ActivityResponse;

public interface ActivityService {
    ActivityResponse getUserActivity(Long id);
    ActivityResponse getUserInitialActivity();
    ActivityResponse evaluate(ActivityEvaluationRequest activityEvaluationRequest) throws MissingAnswerException;
}
