package com.example.rabbitmqconsumer_05_01.controller;

import com.example.rabbitmqconsumer_05_01.repo.EmailTemplateRepo;
import com.example.rabbitmqconsumer_05_01.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email-sending")
@AllArgsConstructor
public class EmailController {
    private EmailService emailService;
    private EmailTemplateRepo emailTemplateRepo;

//    @GetMapping()
//    public String emailSending(
//            @RequestParam("recipientName") final String recipientName,
//            @RequestParam("recipientEmail") final String recipientEmail,
//            @RequestParam final ActionTypeEnum actionType
//    ) throws MessagingException {
//        emailService.sendHTMLEmail("nhdao.it.learn@gmail.com", recipientEmail,
//                emailTemplateRepo.getContentByActionType(actionType), "This is a mail!");
//        return "Successful!";
//    }
}
