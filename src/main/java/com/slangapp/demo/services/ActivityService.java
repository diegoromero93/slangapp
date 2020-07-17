package com.slangapp.demo.services;

import com.slangapp.demo.controllers.responses.ActivityResponse;

public interface ActivityService {
    ActivityResponse getUserActivity(Long id);
}
