package com.example.demo.dto.response

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val authorName: String,
    val text: String,
    val createdAt: LocalDateTime
)