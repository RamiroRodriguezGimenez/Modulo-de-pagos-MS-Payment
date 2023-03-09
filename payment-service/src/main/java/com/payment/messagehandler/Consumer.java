package com.payment.messagehandler;

import com.payment.entity.Transaction;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class Consumer {

   /* @JmsListener(destination = "Nombre de cola")
    public void reciveMessage(Message message) throws JMSException {

        Transaction transaction=message.getBody(Transaction.class);
        System.out.println(transaction);
    }*/



}
