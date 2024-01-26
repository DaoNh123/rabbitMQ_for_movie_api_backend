package com.example.rabbitmqconsumer_05_01.entity;

import com.example.rabbitmqconsumer_05_01.dto.OrderResponseInfo.OrderResponse;
import com.example.rabbitmqconsumer_05_01.enums.ActionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RabbitMQMessage <T>{
    private ActionTypeEnum actionType;          // ORDER_CREATED, UPCOMING_MOVIE
    private T data;
    private String destinationEmail;
}
