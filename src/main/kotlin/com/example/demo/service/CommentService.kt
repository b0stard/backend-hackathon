package com.example.demo.service

import com.example.demo.dto.request.CreateCommentRequest
import com.example.demo.dto.response.CommentResponse
import com.example.demo.entity.Comment
import com.example.demo.repository.CommentRepository
import com.example.demo.repository.TaskRepository
import com.example.demo.repository.UserRepository
import jakarta.transaction.Transactional

import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository
) {

    fun getByTask(taskId: Long): List<CommentResponse> {
        return commentRepository.findAllByTaskId(taskId)
            .map { it.toResponse() }
    }

    @Transactional
    fun create(taskId: Long, request: CreateCommentRequest): CommentResponse {
        val task = taskRepository.findById(taskId)
            .orElseThrow { RuntimeException("Task not found") }

        val author = userRepository.findById(request.authorId)
            .orElseThrow { RuntimeException("User not found") }

        val comment = Comment(
            text = request.text,
            author = author,
            task = task
        )

        return commentRepository.save(comment).toResponse()
    }

    private fun Comment.toResponse(): CommentResponse {
        return CommentResponse(
            id = this.id!!,
            text = this.text,
            authorName = this.author.name,
            createdAt = this.createdAt
        )
    }
}