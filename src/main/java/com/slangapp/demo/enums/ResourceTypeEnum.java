package com.slangapp.demo.enums;

import lombok.Getter;

@Getter
public enum ResourceTypeEnum {

    AUDIO("AUDIO", "This is a mp3 audio file"),
    IMAGE("IMAGE", "This is a JPG file"),
    ETC("ETC", "etc ....");

    private String code;
    private String description;

    ResourceTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static ResourceTypeEnum getResourceTypeEnum(final String code){
        ResourceTypeEnum resourceTypeEnum = null;
        for(ResourceTypeEnum activeEnum :  values()){
            if (activeEnum.getCode().equals(code)) {
                resourceTypeEnum = activeEnum;
                break;
            }
        }
        return resourceTypeEnum;
    }
}
