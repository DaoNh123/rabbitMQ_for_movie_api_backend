package com.example.rabbitmqconsumer_05_01.repo;

import com.example.rabbitmqconsumer_05_01.entity.EmailTemplate;
import com.example.rabbitmqconsumer_05_01.enums.ActionTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateRepo extends JpaRepository<EmailTemplate, Long> {
    @Query(value = "SELECT et.htmlContent FROM EmailTemplate et " +
            "WHERE et.actionType = :actionType")
    String getContentByActionType(ActionTypeEnum actionType);
}
