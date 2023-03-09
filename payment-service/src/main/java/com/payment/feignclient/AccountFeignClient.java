package com.payment.feignclient;

import com.payment.dto.request.TransactionRequest;
import com.payment.dto.response.InternalResponse;
import com.payment.entity.CardTransaction;
import com.payment.entity.CommonTransaction;
import com.payment.entity.Transaction;
import com.payment.entity.Type;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value="api-gateway")
public interface AccountFeignClient {

    @PutMapping("account-service/api/account/validate")
    InternalResponse validate(@RequestBody Transaction transaction, @RequestHeader("Authorization") String authorizationHeader);

    @PutMapping("account-service/api/account/executeTransaction")
    InternalResponse execute(@RequestBody Transaction transaction, @RequestHeader("Authorization") String authorizationHeader);

    @GetMapping("/account-service/api/account/is-active/{accountNumber}")
    InternalResponse validate(@PathVariable String accountNumber, @RequestHeader("Authorization") String authorizationHeader);

    @PutMapping("/account-service/api/account/confirmPayment")
    ResponseEntity<Object> confirm(@RequestBody Transaction transaction, @RequestHeader("Authorization") String authorizationHeader);

    @PutMapping("/account-service/api/account/validate-card")
    InternalResponse validate(@RequestBody CardTransaction transaction, @RequestHeader("Authorization") String authorizationHeader);

}
