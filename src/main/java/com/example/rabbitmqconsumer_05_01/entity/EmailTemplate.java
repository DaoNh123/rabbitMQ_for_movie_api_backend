package com.example.rabbitmqconsumer_05_01.entity;

import com.example.rabbitmqconsumer_05_01.enums.ActionTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "email_teamplates")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EmailTemplate extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private ActionTypeEnum actionType;
    @Column(columnDefinition = "TEXT")
    private String htmlContent;
}
