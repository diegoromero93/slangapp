package com.slangapp.demo.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.slangapp.demo.controllers.responses.ResourceResponse;
import com.slangapp.demo.enums.ActivityTypeEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ActivityAbstract implements ActivityInterface, Serializable {
    @JsonProperty("activity_type")
    private ActivityTypeEnum activityTypeEnum;
    private List<ResourceResponse> resources;

    @JsonProperty("activity_id")
    private Long activityId;

    @JsonProperty("current_xp")
    private Integer currentXP;


    private String message;
    private Boolean correct;
}
