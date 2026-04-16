package com.example.demo.repository

import com.example.demo.entity.Task
import com.example.demo.enums.TaskPriority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Optional
@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    @Query("""
        select t
        from Task t
        join fetch t.author
        join fetch t.assignee
        join fetch t.department
        order by t.createdAt desc
    """)
    fun findAllWithRelations(): List<Task>

    @Query("""
        select t
        from Task t
        join fetch t.author
        join fetch t.assignee
        join fetch t.department
        where t.assignee.id = :assigneeId
        order by t.createdAt desc
    """)
    fun findAllByAssigneeIdWithRelations(
        @Param("assigneeId") assigneeId: Long
    ): List<Task>

    @Query("""
        select t
        from Task t
        join fetch t.author
        join fetch t.assignee
        join fetch t.department
        where (:assigneeId is null or t.assignee.id = :assigneeId)
          and (:priority is null or t.priority = :priority)
          and (:title is null or lower(t.title) like lower(concat('%', :title, '%')))
          and (:startDate is null or t.deadline >= :startDate)
          and (:endDate is null or t.deadline <= :endDate)
        order by t.createdAt desc
    """)
    fun findByFilters(
        @Param("assigneeId") assigneeId: Long?,
        @Param("priority") priority: TaskPriority?,
        @Param("title") title: String?,
        @Param("startDate") startDate: LocalDateTime?,
        @Param("endDate") endDate: LocalDateTime?
    ): List<Task>

    @Query("""
        select t
        from Task t
        join fetch t.author
        join fetch t.assignee
        join fetch t.department
        where t.id = :id
    """)
    fun findByIdWithRelations(@Param("id") id: Long): Optional<Task>
}