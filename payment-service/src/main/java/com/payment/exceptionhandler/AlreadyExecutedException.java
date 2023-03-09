package com.payment.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_GATEWAY)
public class AlreadyExecutedException extends RuntimeException{

    public AlreadyExecutedException() {super();}

    public AlreadyExecutedException (String message) {
        super(message);
    }
}
