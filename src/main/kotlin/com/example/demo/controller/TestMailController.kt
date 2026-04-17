package com.example.demo.controller

import com.example.demo.service.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/mail")
class TestMailController(
    private val emailService: EmailService
) {

    @PostMapping("/send")
    fun sendMail(@RequestParam to: String): ResponseEntity<Any> {
        return try {
            emailService.sendSimpleEmail(
                to = to,
                subject = "Test email",
                text = "Привет! Это тестовое письмо."
            )
            ResponseEntity.ok("Email sent")
        } catch (e: Exception) {
            ResponseEntity.status(500).body(e.message ?: "Mail send error")
        }
    }
}