package com.example.demo.dto.response


import java.time.LocalDateTime

data class TaskHistoryResponse(
    val id: Long,
    val field: String,
    val oldValue: String?,
    val newValue: String?,
    val changedByUserId: Long,
    val changedByUserName: String,
    val changedAt: LocalDateTime
)