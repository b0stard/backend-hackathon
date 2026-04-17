package com.example.demo.dto.request

import com.example.demo.enums.TaskStatus
import jakarta.validation.constraints.NotNull

data class UpdateTaskStatusRequest(
    @field: NotNull
    val status: TaskStatus
)