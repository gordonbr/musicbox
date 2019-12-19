package org.jonathas.musicbox.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.InvalidParameterException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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
        logger.error(ex.getMessage());
        ApiError apiError = new ApiError(BAD_REQUEST, "settingId is required", ex);

        return buildResponseEntity(apiError, ex);
    }

    @ExceptionHandler(InvalidParameterException.class)
    protected ResponseEntity<Object> handleGeneralInvalidParameterException(Exception ex) {
        logger.error(ex.getMessage());
        ApiError apiError = new ApiError(BAD_REQUEST, ex.getMessage(), ex);
        return buildResponseEntity(apiError, ex);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGeneralException(Exception ex) {
        logger.error(ex.getMessage());
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, "settingId is required", ex);
        return buildResponseEntity(apiError, ex);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError, Exception ex) {
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), null);
    }

}
