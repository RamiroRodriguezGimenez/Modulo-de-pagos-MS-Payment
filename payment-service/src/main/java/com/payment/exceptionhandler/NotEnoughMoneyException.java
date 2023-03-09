package com.payment.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class NotEnoughMoneyException extends RuntimeException{

    public NotEnoughMoneyException() {super();}

    public NotEnoughMoneyException (String message) {
        super(message);
    }
}
