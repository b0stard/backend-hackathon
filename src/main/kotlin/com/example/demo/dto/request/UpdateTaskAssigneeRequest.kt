package com.example.demo.dto.request

import jakarta.validation.constraints.NotNull

data class UpdateTaskAssigneeRequest(
    @field: NotNull
    val assigneeId: Long
)

