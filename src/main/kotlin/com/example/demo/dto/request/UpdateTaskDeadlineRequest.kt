package com.example.demo.dto.request


import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class UpdateTaskDeadlineRequest(
    @field: NotNull
    @field: Future
    val deadline: LocalDateTime
)