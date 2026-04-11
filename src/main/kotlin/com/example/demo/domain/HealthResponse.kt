package com.example.demo.domain

data class HealthResponse(
    val status: String,
    val service: String,
    val port: String?
)