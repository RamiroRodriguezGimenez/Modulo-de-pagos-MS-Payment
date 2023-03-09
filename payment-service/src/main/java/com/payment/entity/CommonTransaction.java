package com.payment.entity;

import com.payment.dto.response.InternalResponse;
import com.payment.feignclient.AccountFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class CommonTransaction extends Transaction{



    public CommonTransaction() {
        super();
    }

    public CommonTransaction(String accountOrigin, String accountDestination, BigDecimal amount) {
        super(accountOrigin, accountDestination, amount);
    }

    @Override
    public InternalResponse validate(AccountFeignClient accountFeignClient, String accessToken){
        InternalResponse response=accountFeignClient.validate(this, accessToken);

        return response;
    }

    @Override
    public InternalResponse execute(AccountFeignClient accountFeignClient, String accessToken){
        this.setStatus(Status.EXECUTED);
        return accountFeignClient.execute(this, accessToken);
    }
}
