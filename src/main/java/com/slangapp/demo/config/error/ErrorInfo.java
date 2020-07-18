package com.slangapp.demo.config.error;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ErrorInfo {
    @JsonProperty
    private String message;

    @JsonProperty("timestamp")
    private Date timestamp;

    @JsonProperty("uri")
    private String uriRequested;

    @JsonProperty("error")
    private Boolean error;

    public ErrorInfo(Exception exception, String uriRequested) {
        this.message = exception.toString();
        this.uriRequested = uriRequested;
        this.timestamp = new Date();
        this.error = true;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUriRequested() {
        return uriRequested;
    }
}