package com.example.rabbitmqconsumer_05_01.dto.OrderResponseInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class OrderResponse {
    private Long id;
    private String customerName;
    private String customerAddress;
    private Long customerAge;
    private String customerEmail;
    private SlotInOrderRes slot;
    private List<OrderDetailInOrderRes> orderDetailList;

    public Double getTotalValue(){
        if(this.orderDetailList.isEmpty()) return 0D;
        return orderDetailList.stream()
                .map(orderDetail -> orderDetail.getSeat().getPrice())
                .reduce(0D, Double::sum);
    }
}
