package com.slangapp.demo.enums;

import lombok.Getter;

@Getter
public enum ResourceType {

    AUDIO("AUDIO", "This is a mp3 audio file"),
    IMAGE("IMAGE", "This is a JPG file"),
    ETC("ETC", "etc ....");

    private String code;
    private String description;

    ResourceType(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static ResourceType getResourceTypeEnum(final String code){
        ResourceType resourceType = null;
        for(ResourceType activeEnum :  values()){
            if (activeEnum.getCode().equals(code)) {
                resourceType = activeEnum;
                break;
            }
        }
        return resourceType;
    }
}
