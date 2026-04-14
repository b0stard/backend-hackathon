package com.example.demo.repository

import com.example.demo.entity.TaskHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TaskHistoryRepository : JpaRepository<TaskHistory, Long> {

    fun findAllByTaskIdOrderByChangedAtDesc(taskId: Long): List<TaskHistory>

    @Query(
        """
        select th
        from TaskHistory th
        join fetch th.changedByUser
        where th.task.id = :taskId
        order by th.changedAt desc
        """
    )
    fun findAllByTaskIdWithChangedByUser(@Param("taskId") taskId: Long): List<TaskHistory>
}