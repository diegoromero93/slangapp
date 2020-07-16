package com.slangapp.demo.enums;

import lombok.Getter;

@Getter
public enum ActivityTypeEnum {

    WORD_SCRAMBLE("WORD_SCRAMBLE", "word scramble activity");

    private String code;
    private String description;

    ActivityTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static ActivityTypeEnum getActivityTypeEnmu(final String code){
        ActivityTypeEnum activityTypeEnum = null;
        for(ActivityTypeEnum activeEnum :  values()){
            if (activeEnum.getCode().equals(code)) {
                activityTypeEnum = activeEnum;
                break;
            }
        }
        return activityTypeEnum;
    }
}
