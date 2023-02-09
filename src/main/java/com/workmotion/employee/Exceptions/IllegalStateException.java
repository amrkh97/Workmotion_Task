package com.workmotion.employee.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class IllegalStateException extends RuntimeException {

    public IllegalStateException(String message) {
        super(message);
    }

    public IllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }
}

