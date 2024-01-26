package com.example.rabbitmqconsumer_05_01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private Character gender;
    private String email;
    private String phoneNumber;
    private String customerAddress;
    private String verificationCode;
}
