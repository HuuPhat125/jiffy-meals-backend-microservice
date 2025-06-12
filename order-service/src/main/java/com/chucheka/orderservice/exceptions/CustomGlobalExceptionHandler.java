package com.chucheka.orderservice.exceptions;

import com.chucheka.orderservice.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException exc) {

        GenericResponse<?> errorResponse = GenericResponse.builder()
                .success(false)
                .message(exc.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFountException(ResourceNotFoundException ex) {

        GenericResponse<?> errorResponse = GenericResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<?> handleClientErrorException(ClientErrorException ex) {

        GenericResponse<?> errorResponse = GenericResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ServerErrorException.class })
    public ResponseEntity<Object> handleAppException(Exception ex) {

        GenericResponse<?> errorResponse = GenericResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}