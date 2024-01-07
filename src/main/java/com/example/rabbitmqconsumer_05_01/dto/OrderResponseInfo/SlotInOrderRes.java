package com.example.rabbitmqconsumer_05_01.dto.OrderResponseInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class SlotInOrderRes {
    private Long slotId;
//    @JsonFormat(pattern="MM/dd/yyyy - HH:mm:ss.SSS Z", timezone="Asia/Ho_Chi_Minh")
//    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime startTime;
//    @JsonFormat(pattern="yyyy-MM-dd 'T' HH:mm:ss.SSSZ", timezone="Europe/Zagreb")
    private ZonedDateTime endTime;
    private MovieInOrderRes movie;
    private String theaterRoom;
}
