package com.slangapp.demo.config.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissingHeaderAuthException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public MissingHeaderAuthException(String message){

        super(message);
    }


}

