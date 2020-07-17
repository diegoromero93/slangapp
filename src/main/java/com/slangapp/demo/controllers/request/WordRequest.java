package com.slangapp.demo.controllers.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class WordRequest {
    @NotBlank
    private String word;
}
