package com.springboot.blogapp.exception;

import com.springboot.blogapp.payload.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(new Date())
                .errorMessage(ex.getMessage())
                .errorDetails(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorResponse> handleBlogAPIException(BlogAPIException ex, WebRequest webRequest){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(new Date())
                .errorMessage(ex.getMessage())
                .errorDetails(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    //Global Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest webRequest){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(new Date())
                .errorMessage(ex.getMessage())
                .errorDetails(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
       Map<String,String> errors = new HashMap<>();
       ex.getBindingResult().getAllErrors().forEach((error)->{
           String fieldName = ((FieldError) error).getField();
           String message = error.getDefaultMessage();
           errors.put(fieldName,message);
       });
       return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
