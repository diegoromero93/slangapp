package com.slangapp.demo.controllers.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class ActivityEvaluationRequest {

    @NonNull
    @NotEmpty
    @JsonProperty("current_activity_id")
    private Long currentActivityId;

    @NonNull
    @NotEmpty
    @Size(min = 1)
    private List<String> answer;

    @JsonProperty("current_xp")
    private Integer currentXP;
}
