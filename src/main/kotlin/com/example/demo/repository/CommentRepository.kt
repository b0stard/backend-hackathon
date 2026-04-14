package com.example.demo.repository

import com.example.demo.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CommentRepository : JpaRepository<Comment, Long> {

    fun findAllByTaskIdOrderByCreatedAtAsc(taskId: Long): List<Comment>

    @Query(
        """
        select c
        from Comment c
        join fetch c.author
        where c.task.id = :taskId
        order by c.createdAt asc
        """
    )
    fun findAllByTaskIdWithAuthor(@Param("taskId") taskId: Long): List<Comment>
}