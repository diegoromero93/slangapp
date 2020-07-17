package com.slangapp.demo.controllers.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;


@Getter
@Setter
public class WordResponse {
    private Long id;
    private String word;
    private String phonetic;
    private Set<ResourceResponse> resources;
    private Date createdAt;
    private Date updatedOn;
}

