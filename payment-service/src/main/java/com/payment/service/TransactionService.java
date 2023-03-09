package com.payment.service;

import brave.Tracer;
import com.payment.dto.response.InternalResponse;
import com.payment.exceptionhandler.AlreadyExecutedException;
import com.payment.exceptionhandler.NotEnoughMoneyException;
import com.payment.exceptionhandler.TransactionNotFoundException;
import com.payment.messagehandler.Publisher;
import com.payment.entity.*;
import com.payment.factory.TransactionFactory;
import com.payment.feignclient.AccountFeignClient;
import com.payment.repository.TransactionRepository;
import com.payment.dto.request.TransactionRequest;


import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
     TransactionFactory factory;

    @Autowired
    AccountFeignClient accountFeignClient;

    @Autowired
    Publisher publisher;

    @Autowired
    private Tracer tracer;

    Logger logger =  LoggerFactory.getLogger(TransactionService.class);
    public ResponseEntity<Object> newTransaction(TransactionRequest request, String accessToken) {

        Transaction transaction;
        InternalResponse response=null;
        try {

            transaction = factory.create(request);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
            logger.info("Transaccion a validar: "+transaction);
            response=validate(transaction, accessToken);
            logger.info("Transaccion validada "+transaction);

           if (response.getStatusCode()==400 ) {
               transaction.setStatus(Status.CANCELED);
               transactionRepository.save(transaction);
               return new ResponseEntity<>(response.getMessage(),HttpStatus.valueOf(response.getStatusCode()));
           }


        logger.info(String.valueOf(response.getStatusCode()));

        if (response.getStatusCode()==403){

            transaction.setStatus(Status.CANCELED);
            transactionRepository.save(transaction);
            return new ResponseEntity<>(response.getMessage(),HttpStatus.valueOf(response.getStatusCode()));
        }
        logger.info("Transaccion a ejecutar "+transaction);
        transactionRepository.save(transaction);
        response= execute(transaction, accessToken);

        transactionRepository.save(transaction);
        logger.info("transaccion guardada "+transaction);
        logger.info(response.getMessage()+" "+response.getStatusCode());
        return new ResponseEntity<>(response.getMessage(),HttpStatus.valueOf(response.getStatusCode()));
    }

    public InternalResponse validate(Transaction transaction, String accessToken){
        InternalResponse response;
    try {
        response = transaction.validate(accountFeignClient, accessToken);
        logger.info("mensaje: "+response.getMessage()+"     "+String.valueOf(response.getStatusCode()));
        return response;
    }catch (NotEnoughMoneyException e){
        throw new NotEnoughMoneyException();
    }
    }

    public InternalResponse execute(Transaction transaction, String accessToken){
        try{
            InternalResponse response = transaction.execute(accountFeignClient, accessToken);
            logger.info(response.getMessage());
            return response;
        } catch (Exception e) {
            tracer.currentSpan().tag("error.msg", "Error at with account-service "+e.getMessage() );
            //volver atras paso de validate
            return new InternalResponse(e.getMessage(), 505);
        }
    }

    public ResponseEntity<Object> confirm (Long id) {
        MoneySend transaction;
        try{
            Optional request= transactionRepository.findById(id);
            if (request.isEmpty()) {
                throw new TransactionNotFoundException("Transaction not found");

                }
            transaction= (MoneySend) request.get();
            logger.info("Se compara: "+transaction.getStatus()+" con "+Status.EXECUTED);
            if (transaction.getStatus()==Status.EXECUTED) {
                throw new AlreadyExecutedException("transaction already executed");
            }
            ResponseEntity response= new ResponseEntity<>("Executed", HttpStatus.ACCEPTED);

            sendMessage(id);
            transaction.setStatus(Status.EXECUTED);
            transactionRepository.save(transaction);
            return response;
       }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> getAll(){
        List<Transaction> transactions;
        try {
            transactions = transactionRepository.findAll();
        } catch (Exception e) {
            return new ResponseEntity<>("Data base conection error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    public ResponseEntity<Object> getByDate(LocalDate fromDate, LocalDate toDate, String accountNumber){
        List<Transaction> transactions;
        try {
            transactions = transactionRepository.findByAccountOriginAndDateBetween(accountNumber, fromDate, toDate);
        }catch (Exception e) {
            return new ResponseEntity<>("Data base conection error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (transactions== null) { return new ResponseEntity<>("No transaction found in that range of dates", HttpStatus.NO_CONTENT);}
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    public void sendMessage(Long id){
        try {
            Optional<Transaction> transaction = transactionRepository.findById(id);

            publisher.sendMessage(transaction.get());
        } catch (Exception e){

        }
    }

}
