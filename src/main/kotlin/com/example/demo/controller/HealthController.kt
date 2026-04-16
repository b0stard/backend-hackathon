package com.example.demo.controller


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class HealthController {

    @GetMapping("/")
    fun root(): Map<String, String> {
        return mapOf(
            "status" to "ok",
            "service" to "backend-hackathon"
        )
    }

    @GetMapping("/api/health")
    fun health(): Map<String, String> {
        return mapOf("status" to "ok")
    }
}