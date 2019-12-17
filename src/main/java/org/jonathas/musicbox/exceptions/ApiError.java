package org.jonathas.musicbox.exceptions;

import org.springframework.http.HttpStatus;

class ApiError {

    private HttpStatus status;
    private String message;
    private String debugMessage;

    ApiError() {}

    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public void setMessage(String message) { this.message = message; }
    public HttpStatus getStatus() { return this.status; }
}
