package com.example.rabbitmqconsumer_05_01.consumer;

import com.example.rabbitmqconsumer_05_01.entity.RabbitMQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;

import static com.example.rabbitmqconsumer_05_01.config.RabbitMQConsumerConfig.*;
import static com.example.rabbitmqconsumer_05_01.consumer.MessagesConsumer.HEADER_X_RETRIES_COUNT;

public class ParkingLotDLQAmqpContainer {
    private static final Logger log = LoggerFactory.getLogger(ParkingLotDLQAmqpContainer.class);
    private final RabbitTemplate rabbitTemplate;
    public static final int MAX_RETRIES_COUNT = 2;

    public ParkingLotDLQAmqpContainer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = QUEUE_MESSAGES_DLQ)
    public void processFailedMessagesRetryWithParkingLot(
            @Payload RabbitMQMessage rabbitMQMessage,
            Message failedMessage
    ) {
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties().getHeaders().get(HEADER_X_RETRIES_COUNT);
        if (retriesCnt == null)
            retriesCnt = 1;
        if (retriesCnt > MAX_RETRIES_COUNT) {
            log.info("**3** Sending message to the parking lot queue");
            rabbitTemplate.send(EXCHANGE_PARKING_LOT, failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
            return;
        }
        log.info("**2** Retrying message for the {} time", retriesCnt);
        failedMessage.getMessageProperties().getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCnt);

        try {
            log.warn("Sleep 6 seconds before try to resend email!");
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        rabbitTemplate.send(EXCHANGE_MESSAGES, failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
    }

    @RabbitListener(queues = QUEUE_PARKING_LOT)
    public void processParkingLotQueue(@Payload RabbitMQMessage rabbitMQMessage,Message failedMessage) {
        log.info("**3.2** Received message in parking lot queue {}", failedMessage.toString());
        /*  Custom to send email or notify this to both customer and admin to solve this problem    */
    }
}
