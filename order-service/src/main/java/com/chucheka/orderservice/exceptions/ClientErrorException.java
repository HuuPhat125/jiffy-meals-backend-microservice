package com.chucheka.orderservice.exceptions;

public class ClientErrorException extends RuntimeException{
    public ClientErrorException(String message) {
        super(message);
    }

    public ClientErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
