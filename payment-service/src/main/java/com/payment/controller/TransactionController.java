package com.payment.controller;

import com.payment.dto.request.TransactionRequest;
import com.payment.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody TransactionRequest request, @RequestHeader("Authorization") String accessToken){
      try{
          return service.newTransaction(request, accessToken);
    }catch (Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll(){
        return service.getAll();
    }

    @GetMapping("/getByDate")
    public ResponseEntity<Object> getByDate(@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate, @RequestParam String accountNumber){
        return service.getByDate(fromDate, toDate, accountNumber);
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<Object> confirm(@PathVariable Long id){
        return service.confirm(id);
    }

    @GetMapping("/send/{id}")
    public void send(@PathVariable Long id){
        service.sendMessage(id);
    }


}
