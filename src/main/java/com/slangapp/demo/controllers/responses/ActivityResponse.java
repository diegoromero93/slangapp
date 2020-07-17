package com.slangapp.demo.controllers.responses;

import com.slangapp.demo.pojos.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActivityResponse {
    private Activity currentActivity;
    private Activity nextActivities;
}
