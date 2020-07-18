package com.slangapp.demo.controllers.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.slangapp.demo.pojos.Activity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {
    @JsonProperty("current_activity")
    private Activity currentActivity;
    @JsonProperty("next_activity")
    private Activity nextActivity;
}
