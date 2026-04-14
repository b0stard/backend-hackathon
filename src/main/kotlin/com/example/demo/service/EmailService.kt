package com.example.demo.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender
) {

    fun sendTaskAssignedEmail(to: String, taskTitle: String, deadline: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.subject = "Вам назначена новая задача"
        message.text = """
            Вам назначена новая задача: $taskTitle
            
            Срок выполнения: $deadline
            
            Пожалуйста, зайдите в систему и ознакомьтесь с деталями.
        """.trimIndent()

        mailSender.send(message)
    }

    fun sendTestEmail(to: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.subject = "Тестовое письмо"
        message.text = "Если вы получили это письмо, SMTP настроен правильно."

        mailSender.send(message)
    }
}