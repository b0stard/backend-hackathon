package com.example.demo.controller

import com.example.demo.dto.response.HealthResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class HealthController {

    @GetMapping("/health")
    fun health(): HealthResponse {
        return HealthResponse(
            status = "ok",
            service = "backend-hackathon",
            port = System.getenv("PORT")
        )
    }
}