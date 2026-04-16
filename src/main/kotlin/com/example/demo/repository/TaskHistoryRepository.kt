package com.example.demo.repository

import com.example.demo.entity.TaskHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TaskHistoryRepository : JpaRepository<TaskHistory, Long> {
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