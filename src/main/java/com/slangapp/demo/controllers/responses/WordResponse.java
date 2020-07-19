package com.slangapp.demo.controllers.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordResponse implements Serializable{
    private Long id;
    private String word;
    private String phonetic;
    private List<ResourceResponse> resources;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_on")
    private Date updatedOn;
}

