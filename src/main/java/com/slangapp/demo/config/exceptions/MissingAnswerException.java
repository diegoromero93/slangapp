package com.slangapp.demo.config.exceptions;

public class MissingAnswerException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public MissingAnswerException(){
        super("Missing answer parameter");
    }

}
