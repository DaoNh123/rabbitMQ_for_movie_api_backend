package com.example.rabbitmqconsumer_05_01.service;

import com.example.rabbitmqconsumer_05_01.dto.OrderResponseInfo.OrderResponse;
import com.example.rabbitmqconsumer_05_01.dto.UserDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailService {
//    @Autowired
    private JavaMailSender mailSender;

//    @Autowired
//    @Qualifier("emailTemplateEngine")
    private TemplateEngine htmlTemplateEngine;

    public void sendCreatedOrderConfirmation(
            final OrderResponse orderResponse,
            String templateContent
    ) throws MessagingException {
        final Context ctx = new Context();

        ctx.setVariable("orderResponse", orderResponse);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Example HTML email (simple)");
        message.setFrom("nhdao.it.learn@gmail.com");
        message.setTo(orderResponse.getCustomerEmail());

//        System.out.println(templateContent);
        final String htmlContent = this.htmlTemplateEngine.process(templateContent, ctx);
        message.setText(htmlContent, true /* isHtml */);
//        // Send email
        this.mailSender.send(mimeMessage);
    }

    public void sendVerifyAccountMessage(
            UserDto data, String contentByActionType, String verifyLink
    ) throws MessagingException {
        final Context ctx = new Context();
        ctx.setVariable("userData", data);
        ctx.setVariable("verifyLink", verifyLink);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Example HTML email (simple)");
        message.setFrom("nhdao.it.learn@gmail.com");
        message.setTo(data.getEmail());

//        System.out.println(templateContent);
        final String htmlContent = this.htmlTemplateEngine.process(contentByActionType, ctx);
        message.setText(htmlContent, true /* isHtml */);
//        // Send email
        this.mailSender.send(mimeMessage);
    }
}
