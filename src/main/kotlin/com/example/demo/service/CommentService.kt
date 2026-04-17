package com.example.demo.service

import com.example.demo.dto.request.CreateCommentRequest
import com.example.demo.dto.response.CommentResponse
import com.example.demo.entity.Comment
import com.example.demo.entity.TaskHistory
import com.example.demo.exception.NotFoundException
import com.example.demo.repository.CommentRepository
import com.example.demo.repository.TaskHistoryRepository
import com.example.demo.repository.TaskRepository
import com.example.demo.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository,
    private val taskHistoryRepository: TaskHistoryRepository
) {
    fun getCommentsByTaskId(taskId: Long): List<CommentResponse> {
        return commentRepository.findAllByTaskIdWithAuthor(taskId).map {
            CommentResponse(
                id = it.id,
                authorId = it.author.id,
                authorName = it.author.name,
                text = it.text,
                createdAt = it.createdAt
            )
        }
    }

    @Transactional
    fun createComment(taskId: Long, request: CreateCommentRequest): CommentResponse {
        val task = taskRepository.findById(taskId)
            .orElseThrow { NotFoundException("Задача не найдена") }

        val author = userRepository.findById(request.authorId)
            .orElseThrow { NotFoundException("Автор не найден ") }

        val comment = Comment(
            task = task,
            author = author,
            text = request.text
        )
        val savedComment = commentRepository.save(comment)

        taskHistoryRepository.save(
            TaskHistory(
                task = task,
                field = "comment",
                oldValue = null,
                newValue = "Добавлен комментарий",
                changedByUser = author
            )
        )

        return CommentResponse(
            id = savedComment.id,
            authorId = savedComment.author.id,
            authorName = savedComment.author.name,
            text = savedComment.text,
            createdAt = savedComment.createdAt
        )
    }
}