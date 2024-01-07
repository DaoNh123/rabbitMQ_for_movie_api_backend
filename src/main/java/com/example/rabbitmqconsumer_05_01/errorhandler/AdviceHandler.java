package com.example.rabbitmqconsumer_05_01.errorhandler;

import com.example.rabbitmqconsumer_05_01.consumer.MessagesConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceHandler {
    private static final Logger log = LoggerFactory.getLogger(AdviceHandler.class);

    @ExceptionHandler(BusinessException.class)
    public void catchBusinessError(BusinessException exception){
        log.error("An error has occur: {}", exception.getMessage());
    }
}
