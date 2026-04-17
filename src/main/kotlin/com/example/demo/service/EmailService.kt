package com.example.demo.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

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

    fun sendTaskAssignedEmail(to: String, taskTitle: String, deadline: String?) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.subject = "Вам назначена новая задача"
        message.text = buildString {
            append("Вам назначена задача: ")
            append(taskTitle)
            if (!deadline.isNullOrBlank()) {
                append(". Дедлайн: ")
                append(deadline)
            }
        }

        mailSender.send(message)
    }
}