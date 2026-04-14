package com.example.demo.dto.response

import java.time.LocalDateTime

data class TaskResponse(
    val id: Long,
    val title: String,
    val shortDescription: String?,
    val status: String,
    val priority: String,
    val deadline: LocalDateTime,
    val createdAt: LocalDateTime,
    val authorId: Long,
    val authorName: String,
    val assigneeId: Long,
    val assigneeName: String,
    val isOverdue: Boolean
)