package com.payment.messagehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.payment.entity.Transaction;
import com.payment.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.Serializable;

@Component
public class Publisher {

    @Autowired
    JmsTemplate jmsTemplate;

    Logger logger =  LoggerFactory.getLogger(Publisher.class);


    public void sendMessage(Transaction message) throws JsonProcessingException {
        logger.info("Se envia el siguiente mensaje: "+message);
        try{
        ;
        ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
        String messageJson = objectMapper.writeValueAsString(message);

        jmsTemplate.convertAndSend("Send Money", messageJson);
        } catch (Exception e){
            logger.error(e.getMessage());
        }

    }
}
