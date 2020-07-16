package com.slangapp.demo.pojos;

import com.slangapp.demo.enums.ActivityTypeEnum;
import com.slangapp.demo.models.Resource;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class ActivityAbstract implements Activity {
    private ActivityTypeEnum activityTypeEnum;
    private Boolean correct;
    private List<Resource> resources;
    private Long activityId;
    private int maxQuantity;
    private String word;
}
