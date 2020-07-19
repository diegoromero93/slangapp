package com.slangapp.demo.config;

import com.slangapp.demo.config.error.ErrorInfo;
import com.slangapp.demo.config.exceptions.MissingHeaderAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.slangapp.demo.config.exceptions.MissingAnswerException;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<ErrorInfo> error(final Exception exception, final HttpStatus httpStatus,
                                            HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorInfo(exception, request.getRequestURI()), httpStatus);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorInfo> handleRuntimeException(HttpServletRequest request, final RuntimeException e) {
        log.error("Exception : ", e);
        return error(e, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = MissingAnswerException.class)
    public ResponseEntity<ErrorInfo> exception(HttpServletRequest request, final RuntimeException e) {
        log.error("Exception : ", e);
        return error(e, HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorInfo> handleRuntimeHttpRequestMethodNotSupportedException(HttpServletRequest request, final HttpRequestMethodNotSupportedException e) {
        log.error("Exception : ", e);
        return error(e, HttpStatus.METHOD_NOT_ALLOWED, request);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorInfo> handleHttpRequestMethodNotSupportedException(HttpServletRequest request, final HttpMessageNotReadableException e) {
        log.error("Exception : ", e);
        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    public ResponseEntity<ErrorInfo> handleMissingRequestHeaderException(HttpServletRequest request, final MissingRequestHeaderException e) {
        log.error("Exception : ", e);
        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = MissingHeaderAuthException.class)
    public ResponseEntity<ErrorInfo> handleMissingHeaderAuthException(HttpServletRequest request, final RuntimeException e) {
        log.error("Exception : ", e);
        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> handleIllegalArgumentException(HttpServletRequest request, final IllegalArgumentException e) {
        log.error("Exception : ", e);
        return error(e, HttpStatus.BAD_REQUEST, request);
    }


}
