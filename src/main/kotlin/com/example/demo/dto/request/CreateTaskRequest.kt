package com.example.demo.dto.request

import com.example.demo.enums.TaskPriority
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class CreateTaskRequest(
    @field: NotBlank
    val title: String,

    val description: String? = null,

    @field: NotNull
    val assigneeId: Long,

    @field: NotNull
    val departmentId: Long,

    @field: NotNull
    val priority: TaskPriority,

    @field: NotNull
    @field: Future
    val deadline: LocalDateTime
)