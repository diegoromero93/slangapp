package com.slangapp.demo.config.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDoesNotMatchSpecs extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;
    public AnswerDoesNotMatchSpecs(String message){
        super(message);
        this.message = message;
    }
}
