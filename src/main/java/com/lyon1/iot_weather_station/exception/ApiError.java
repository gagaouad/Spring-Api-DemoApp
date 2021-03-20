package com.lyon1.iot_weather_station.exception;

import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public class ApiError {

    private int status;
    private HttpStatus error;
    private Timestamp timestamp;
    private String message;
    private String debugMessage;

    private ApiError(HttpStatus status) {
        this.status = status.value();
        this.error = status;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    ApiError(Throwable e) {
        this(HttpStatus.INTERNAL_SERVER_ERROR);
        this.message = e.getMessage();
        this.debugMessage = e.getLocalizedMessage();
    }

    ApiError(HttpStatus status, Throwable e) {
        this(status);
        this.message = e.getMessage();
        this.debugMessage = e.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message) {
        this(status);
        this.message = message;
    }

    ApiError(HttpStatus status, String message, String debugMessage) {
        this(status);
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public HttpStatus getError() {
        return error;
    }

    public void setError(HttpStatus error) {
        this.error = error;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }
}