package com.slangapp.demo.controllers.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.slangapp.demo.pojos.ActivityInterface;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {
    @JsonProperty("current_activity")
    private ActivityInterface currentActivityInterface;
    @JsonProperty("next_activity")
    private ActivityInterface nextActivityInterface;
}
