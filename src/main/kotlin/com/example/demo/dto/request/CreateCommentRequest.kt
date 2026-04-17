package com.example.demo.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateCommentRequest(
    @field: NotNull
    val authorId: Long,

    @field: NotBlank
    val text: String
)