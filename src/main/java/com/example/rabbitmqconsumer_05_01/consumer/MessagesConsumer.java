package com.example.rabbitmqconsumer_05_01.consumer;

import com.example.rabbitmqconsumer_05_01.dto.OrderResponseInfo.OrderResponse;
import com.example.rabbitmqconsumer_05_01.dto.UserDto;
import com.example.rabbitmqconsumer_05_01.entity.RabbitMQMessage;
import com.example.rabbitmqconsumer_05_01.errorhandler.BusinessException;
import com.example.rabbitmqconsumer_05_01.repo.EmailTemplateRepo;
import com.example.rabbitmqconsumer_05_01.service.EmailService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import static com.example.rabbitmqconsumer_05_01.config.RabbitMQConsumerConfig.QUEUE_MESSAGES;
import static com.example.rabbitmqconsumer_05_01.config.RabbitMQConsumerConfig.USER_CREATE_QUEUE_MESSAGES;


@Configuration
public class MessagesConsumer {
    public static final String HEADER_X_RETRIES_COUNT = "x-retries-count";
    private static final Logger log = LoggerFactory.getLogger(MessagesConsumer.class);
    private final RabbitTemplate rabbitTemplate;
    private final EmailService emailService;
    private EmailTemplateRepo emailTemplateRepo;
    @Value("${frontend.endpoint}")
    private String frontendEndpoint;

    public MessagesConsumer(RabbitTemplate rabbitTemplate, EmailService emailService, EmailTemplateRepo emailTemplateRepo) {
        this.rabbitTemplate = rabbitTemplate;
        this.emailService = emailService;
        this.emailTemplateRepo = emailTemplateRepo;
    }

    @RabbitListener(queues = QUEUE_MESSAGES)
    public void receiveMessage(@Payload RabbitMQMessage<OrderResponse> rabbitMQMessage, Message message) throws BusinessException, MessagingException {
        log.info("**1** Received message in {}    : {}", QUEUE_MESSAGES, message.toString());
        System.out.println("@Payload RabbitMQMessage: " + rabbitMQMessage);
        OrderResponse orderResponse = rabbitMQMessage.getData();
        System.out.println(orderResponse);

        emailService.sendCreatedOrderConfirmation(
                rabbitMQMessage.getData(),
                emailTemplateRepo.getContentByActionType(rabbitMQMessage.getActionType())
        );
        log.info("___Successful send message with dataId = {} ___", (rabbitMQMessage.getData()).getCustomerEmail());
    }

    @RabbitListener(queues = USER_CREATE_QUEUE_MESSAGES)
    public void receiveUserCreatedMessage(@Payload RabbitMQMessage<UserDto> rabbitMQMessage, Message message) throws BusinessException, MessagingException {
        log.info("**1** Received message in {}    : {}", QUEUE_MESSAGES, message.toString());
        System.out.println("@Payload RabbitMQMessage: " + rabbitMQMessage);

        String verifyRootEndpoint = frontendEndpoint + "/verify.html";
        String verifyLink = String.join("", verifyRootEndpoint, "?verifyCode=",
                rabbitMQMessage.getData().getVerificationCode(), "&email=",
                rabbitMQMessage.getData().getEmail());

        emailService.sendVerifyAccountMessage(
                rabbitMQMessage.getData(),
                emailTemplateRepo.getContentByActionType(rabbitMQMessage.getActionType()),
                verifyLink
        );
        log.info("___Successful send message with dataId = {} ___", (rabbitMQMessage.getData()).getEmail());
    }

    @Bean
    public ParkingLotDLQAmqpContainer parkingLotDLQAmqpContainer() {
        return new ParkingLotDLQAmqpContainer(rabbitTemplate);
    }
}