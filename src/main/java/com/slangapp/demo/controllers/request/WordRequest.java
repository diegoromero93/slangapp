package com.slangapp.demo.controllers.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class WordRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    private String word;
}
