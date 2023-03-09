package com.payment.factory;

import com.payment.dto.request.TransactionRequest;
import com.payment.entity.*;
import org.springframework.stereotype.Component;

@Component
public class TransactionFactory {

    public Transaction create(TransactionRequest request) throws Exception{
        switch (request.getType()){
            case ECHEQ:
                return new Echeq(request.getAccountOrigin(), request.getAccountDestination(), request.getAmount(), request.getEffectiveDate());
            case MONEYSEND:
                return new MoneySend(request.getAccountOrigin(), request.getAccountDestination(), request.getAmount(), request.getEffectiveDate());
            case CARDTRANSACTION:
                return new CardTransaction(request.getAccountOrigin(), request.getAccountDestination(), request.getAmount(), request.getCardNumber());
            case COMMONTRANSACTION:
                return new CommonTransaction(request.getAccountOrigin(), request.getAccountDestination(), request.getAmount());
            default:
                throw new Exception("Transaction type not valid");
        }
    }
}
