package com.example.rabbitmqconsumer_05_01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.Collections;

@Configuration
public class EmailConfig {
    public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

    @Bean
    @Primary
    /*  By default, the application create a Bean for Template. in fact, I don't find the reason why it happen but*/
    public TemplateEngine emailTemplateEngine(){
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(stringTemplateResolver());
        return templateEngine;
    }

    private StringTemplateResolver stringTemplateResolver() {
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }
}
