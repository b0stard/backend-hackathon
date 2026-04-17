package com.example.demo.dto.response

import java.time.LocalDateTime

data class TaskDetailsResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val status: String,
    val priority: String,
    val deadline: LocalDateTime,
    val createdAt: LocalDateTime,
    val author: UserShortResponse,
    val assignee: UserShortResponse,
    val department: DepartmentResponse,
    val isOverdue: Boolean,
    val comments: List<CommentResponse>,
    val history: List<TaskHistoryResponse>
)