package com.example.demo.repository

import com.example.demo.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
    interface CommentRepository : JpaRepository<Comment, Long> {
        fun findAllByTaskId(taskId: Long): List<Comment>
    }