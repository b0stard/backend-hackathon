package com.example.demo.controller

import com.example.demo.dto.request.CreateCommentRequest
import com.example.demo.dto.response.CommentResponse
import com.example.demo.service.CommentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments")
class CommentController(
    private val commentService: CommentService
) {

    @GetMapping("/task/{taskId}")
    fun getByTask(@PathVariable taskId: Long): List<CommentResponse> {
        return commentService.getByTask(taskId)
    }

    @PostMapping("/task/{taskId}")
    fun create(
        @PathVariable taskId: Long,
        @RequestBody request: CreateCommentRequest
    ): CommentResponse {
        return commentService.create(taskId, request)
    }
}