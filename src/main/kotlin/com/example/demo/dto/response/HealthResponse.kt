package com.example.demo.dto.response

data class HealthResponse(
    val status: String,
    val service: String,
    val port: String?
)