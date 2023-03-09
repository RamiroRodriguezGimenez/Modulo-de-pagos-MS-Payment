package com.payment.entity;

import com.payment.dto.response.InternalResponse;
import com.payment.feignclient.AccountFeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
public class Echeq extends Transaction{

    private LocalDate effectiveDate;

    public Echeq() {
        super();
    }

    public Echeq(String accountOrigin, String accountDestination, BigDecimal amount, LocalDate effectiveDate) {
        super(accountOrigin, accountDestination, amount);
        this.effectiveDate=effectiveDate;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public InternalResponse validate(AccountFeignClient accountFeignClient, String accessToken){

        return accountFeignClient.validate(this, accessToken);
    }

    @Override
    public InternalResponse execute(AccountFeignClient accountFeignClient, String accessToken){

        this.setStatus(Status.OUTSTANDING);
        return new InternalResponse("The transaction has been accepted, it would became effective on"+effectiveDate.toString(), 202);
    }


}
