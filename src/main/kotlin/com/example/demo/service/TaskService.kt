package com.example.demo.service

import com.example.demo.dto.request.CreateTaskRequest
import com.example.demo.dto.request.UpdateTaskStatusRequest
import com.example.demo.dto.response.TaskResponse
import com.example.demo.entity.Task
import com.example.demo.enums.TaskStatus
import com.example.demo.exception.NotFoundException
import com.example.demo.repository.DepartmentRepository
import com.example.demo.repository.TaskRepository
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository,
    private val departmentRepository: DepartmentRepository,
    private val emailService: EmailService
) {

    fun getAll(): List<TaskResponse> {
        return taskRepository.findAll().map {
            TaskResponse(
                id = it.id,
                title = it.title,
                userName = it.assignee?.name,
                departmentName = it?.department?.name,
            )
        }
    }


    fun getTaskById(id: Long): TaskResponse? {
        return taskRepository.findById(id)
            .map { it.toTaskResponse() }
            .orElse(null)
    }

    @Transactional
    fun createTask(request: CreateTaskRequest, authorId: Long): TaskResponse {
        val author = userRepository.findById(authorId)
            .orElseThrow { NotFoundException("Автор задачи не найден") }

        val assignee = userRepository.findById(request.assigneeId)
            .orElseThrow { NotFoundException("Исполнитель не найден") }

        val department = departmentRepository.findById(request.departmentId)
            .orElseThrow { NotFoundException("Отдел не найден") }

        val task = Task(
            title = request.title,
            description = request.description,
            priority = request.priority,
            deadline = request.deadline,
            author = author,
            assignee = assignee,
            department = department,
            status = TaskStatus.NEW
        )

        val savedTask = taskRepository.save(task)

        try {
            emailService.sendTaskAssignedEmail(
                to = assignee.email,
                taskTitle = savedTask.title,
                deadline = savedTask.deadline.toString()
            )
        } catch (_: Exception) {

        }

        return savedTask.toTaskResponse()
    }

    @Transactional
    fun updateTaskStatus(taskId: Long, request: UpdateTaskStatusRequest): TaskResponse {
        val task = taskRepository.findById(taskId)
            .orElseThrow { NotFoundException("Задача не найдена") }

        task.status = request.status
        task.updatedAt = LocalDateTime.now()

        val savedTask = taskRepository.save(task)
        return savedTask.toTaskResponse()
    }

    private fun Task.toTaskResponse(): TaskResponse {
        return TaskResponse(
            id = this.id,
            title = this.title,
            userName = this.assignee?.name,
            departmentName = this.assignee?.department?.name
        )
    }
}