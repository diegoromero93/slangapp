package com.slangapp.demo.enums;

import lombok.Getter;

@Getter
public enum  ActivityType {

    WORD_SCRAMBLE("WORD_SCRAMBLE", "word scramble activity");

    private String code;
    private String description;

    ActivityType(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static ActivityType getActivityTypeEnmu(final String code){
        ActivityType activityType = null;
        for(ActivityType activeEnum :  values()){
            if (activeEnum.getCode().equals(code)) {
                activityType = activeEnum;
                break;
            }
        }
        return activityType;
    }
}
