package com.example.demo.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EmailService(
    private val mailSender: JavaMailSender
) {

    fun sendSimpleEmail(to: String, subject: String, text: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.subject = subject
        message.text = text

        mailSender.send(message)
    }

    fun sendTaskAssignedEmail(
        to: String,
        taskTitle: String,
        deadline: LocalDateTime?
    ) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.subject = "Вам назначена новая задача"
        message.text = "Вам назначена задача: $taskTitle. Дедлайн: $deadline"
        mailSender.send(message)
    }
}