package com.slangapp.demo.controllers.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResourceResponse {
    private Long id;
    private String resourceUrl;
    private String resourceType;
    private Date createdAt;
    private Date updatedOn;
}
