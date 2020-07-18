package com.slangapp.demo.controllers.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResourceResponse {
    private Long id;
    @JsonProperty("resource_url")
    private String resourceUrl;

    @JsonProperty("resource_type")
    private String resourceType;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_on")
    private Date updatedOn;
}
