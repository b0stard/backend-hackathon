package com.example.demo.controller

import com.example.demo.service.EmailService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test-mail")
@Tag(name = "Тест почты", description = "Проверка SMTP отправки")
class TestMailController(
    private val emailService: EmailService
) {

    @Operation(
        summary = "Отправить тестовое письмо",
        description = "Отправляет тестовое письмо на указанный email"
    )
    @GetMapping
    fun sendTestMail(@RequestParam email: String): Map<String, String> {
        emailService.sendTestEmail(email)
        return mapOf("message" to "Письмо отправлено")
    }
}