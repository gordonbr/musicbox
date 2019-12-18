package org.jonathas.musicbox.exceptions;

import org.jonathas.musicbox.controller.JukeBoxController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        logger.error(ex.getMessage());

        ApiError apiError = new ApiError(NOT_FOUND, "JukeboxSetting not found for the given settingId", ex);

        return buildResponseEntity(apiError, ex);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //return super.handleMissingServletRequestParameter(ex, headers, status, request);.
        logger.error(ex.getMessage());
        ApiError apiError = new ApiError(BAD_REQUEST, "settingId is required", ex);

        return buildResponseEntity(apiError, ex);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError, Exception ex) {
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), null);
    }

}
