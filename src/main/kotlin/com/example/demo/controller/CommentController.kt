package com.example.demo.controller

import com.example.demo.dto.request.CreateCommentRequest
import com.example.demo.dto.response.CommentResponse
import com.example.demo.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
@Tag(name = "Комментарии", description = "Методы для работы с комментариями по задачам")
class CommentController(
    private val commentService: CommentService
) {

    @Operation(
        summary = "Получить комментарии задачи",
        description = "Возвращает список комментариев для конкретной задачи"
    )
    @GetMapping
    fun getCommentsByTaskId(@PathVariable taskId: Long): List<CommentResponse> {
        return commentService.getCommentsByTaskId(taskId)
    }

    @Operation(
        summary = "Добавить комментарий к задаче",
        description = "Создает новый комментарий и фиксирует это событие в истории задачи"
    )
    @PostMapping
    fun createComment(
        @PathVariable taskId: Long,
        @Valid @RequestBody request: CreateCommentRequest
    ): CommentResponse {
        return commentService.createComment(taskId, request)
    }
}