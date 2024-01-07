package com.example.rabbitmqconsumer_05_01;

import com.example.rabbitmqconsumer_05_01.dto.OrderResponseInfo.OrderDetailInOrderRes;
import com.example.rabbitmqconsumer_05_01.dto.OrderResponseInfo.OrderResponse;
import com.example.rabbitmqconsumer_05_01.dto.OrderResponseInfo.SeatResponseInOrderRes;
import com.example.rabbitmqconsumer_05_01.dto.OrderResponseInfo.SlotInOrderRes;
import com.example.rabbitmqconsumer_05_01.entity.EmailTemplate;
import com.example.rabbitmqconsumer_05_01.enums.ActionTypeEnum;
import com.example.rabbitmqconsumer_05_01.repo.EmailTemplateRepo;
import com.example.rabbitmqconsumer_05_01.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class RabbitMqConsumer0501Application {
    private EmailTemplateRepo emailTemplateRepo;
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqConsumer0501Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return runner -> {
            if (!emailTemplateRepo.existsById(1L)) {
                generateData();
            }
//            sendEmail();
        };
    }

    private void sendEmail() throws MessagingException {
        ZoneId zoneId = ZoneId.of("UTC");
        OrderResponse orderResponse = OrderResponse.builder()
                .customerName("Dao")
                .customerAge(18L)
                .customerEmail("cocon321235@gmail.com")
                .slot(SlotInOrderRes.builder()
                        .slotId(14L)
                        .startTime(ZonedDateTime.of(2024, 1, 1, 12, 15, 0, 0, zoneId))
                        .endTime(ZonedDateTime.of(2024, 1, 25, 12, 15, 0, 0, zoneId))
                        .build()
                )
                .build();

        OrderDetailInOrderRes orderDetail1 = OrderDetailInOrderRes.builder()
                .orderDetailId(1L)
                .seat(SeatResponseInOrderRes.builder()
                        .id(289L)
                        .seatName("A1")
                        .seatClass("NOR")
                        .price(160D)
                        .build()
                ).build();

        OrderDetailInOrderRes orderDetail2 = OrderDetailInOrderRes.builder()
                .orderDetailId(2L)
                .seat(SeatResponseInOrderRes.builder()
                        .id(290L)
                        .seatName("A2")
                        .seatClass("VIP")
                        .price(200D)
                        .build()
                ).build();

        orderResponse.setOrderDetailList(List.of(
                orderDetail1, orderDetail2
        ));

//        String templateContent = "<h1 th:text=\"${name}\"></h1>\n<p th:text=\"${age}\"></p>";
        String templateContent = emailTemplateRepo.getContentByActionType(ActionTypeEnum.ORDER_CREATED);
        emailService.sendCreatedOrderConfirmation(orderResponse, templateContent);
    }

    private void generateData() {
        EmailTemplate emailTemplate1 = EmailTemplate.builder()
                .actionType(ActionTypeEnum.ORDER_CREATED)
                .htmlContent("""
                                <!DOCTYPE html>
                                <html xmlns:th="http://www.thymeleaf.org">
                                <head>
                                    <title th:remove="all">Template for HTML email (simple)</title>
                                    <link rel="stylesheet" href="../css/style.css">
                                    <style>
                                        table {
                                            font-family: arial, sans-serif;
                                            border-collapse: collapse;
                                            width: 100%;
                                        }

                                        td,
                                        th {
                                            border: 1px solid #dddddd;
                                            text-align: left;
                                            padding: 8px;
                                        }

                                        tr:nth-child(even) {
                                            background-color: #dddddd;
                                        }

                                        #total {
                                            font-weight: bold;
                                            color: blue;
                                            background-color: azure;
                                        }
                                    </style>
                                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                                </head>
                                <body>
                                <h2>Order Created</h2>
                                <p>Thank you because choosing our service! Here is your order details: </p>

                                <table>
                                    <tr>
                                        <th>STT</th>
                                        <th>Seat</th>
                                        <th>seatClass</th>
                                        <th>Price</th>
                                    </tr>

                                    <tr th:each="orderDetail, orderIndex: ${orderResponse.getOrderDetailList()}">
                                        <td th:text="${orderIndex.index + 1}" />
                                        <td th:text="${orderDetail.getSeat().getSeatName()}" />
                                        <td th:text="${orderDetail.getSeat().getSeatClass()}" />
                                        <td th:text="${orderDetail.getSeat().getPrice()}" />
                                    </tr>

                                    <tr id="total">
                                        <td colspan="3">Total</td>
                                        <td th:text="${orderResponse.getTotalValue()}"></td>
                                    </tr>
                                </table>
                                </body>
                                </html>
                        """)
                .build();
        EmailTemplate emailTemplate2 = EmailTemplate.builder()
                .actionType(ActionTypeEnum.UPCOMING_MOVIE)
                .htmlContent("""
                        <!DOCTYPE html>
                        <html>
                        <head>
                        <title>Page Title</title>
                        </head>
                        <body>
                                                
                        <h1>This is UPCOMING_MOVIE email</h1>
                        <p>This is a paragraph.</p>
                                                
                        </body>
                        </html>
                        """)
                .build();

        List<EmailTemplate> emailTemplateList = List.of(
                emailTemplate1, emailTemplate2
        );
        emailTemplateRepo.saveAll(emailTemplateList);
    }
}
