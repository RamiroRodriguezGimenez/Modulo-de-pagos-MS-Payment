package com.payment.entity;

import com.payment.dto.response.InternalResponse;
import com.payment.feignclient.AccountFeignClient;
import org.springframework.http.ResponseEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;
@Entity
public class CardTransaction extends Transaction{

    private String cardNumber;

    public CardTransaction() {
        super();
    }

    public CardTransaction(String accountOrigin, String accountDestination, BigDecimal amount, String cardNumber) {
        super(accountOrigin, accountDestination, amount);
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public InternalResponse validate(AccountFeignClient accountFeignClient, String accessToken){


           InternalResponse response = accountFeignClient.validate(this, accessToken);
            this.setAccountOrigin(response.getMessage());
            return response;



    }

    @Override
    public InternalResponse execute(AccountFeignClient accountFeignClient, String accessToken){
        this.setStatus(Status.EXECUTED);
        return accountFeignClient.execute(this, accessToken);
    }

    @Override
    public String toString() {
        return "CardTransaction{" +
                "cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
