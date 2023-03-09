package com.payment.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseStatusExceptionResolver extends ResponseEntityExceptionHandler {

    private Map<String, Object> response = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(RestResponseStatusExceptionResolver.class);



   /* @ExceptionHandler({AccountInactived.class})
    public ResponseEntity<Object> AccountInactivedException(RuntimeException re){
        response.clear();
        response.put("error", re.getMessage() );
        response.put("status code", HttpStatus.NOT_FOUND.value());
        response.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }*/

    @ExceptionHandler({NotEnoughMoneyException.class})
    public ResponseEntity<Object> handleNotEnoughMoneyException(NotEnoughMoneyException re){
        response.clear();
        response.put("error", re.getMessage() );
        response.put("status code", HttpStatus.FORBIDDEN.value());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({TransactionNotFoundException.class})
    public ResponseEntity<Object> handleTransactionnotFoundException(TransactionNotFoundException re){
        response.clear();
        response.put("error", re.getMessage() );
        response.put("status code", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler({AlreadyExecutedException.class})
    public ResponseEntity<Object> handleAlreadyExecutedException(AlreadyExecutedException re){
        response.clear();
        response.put("error", re.getMessage() );
        response.put("status code", HttpStatus.BAD_GATEWAY.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }
}
