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
public class RabbitMQMessage{
    private ActionTypeEnum actionType;          // ORDER_CREATED, UPCOMING_MOVIE
    private OrderResponse data;

}
