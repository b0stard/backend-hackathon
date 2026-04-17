package com.example.demo.service

import com.example.demo.dto.request.CreateTaskRequest
import com.example.demo.dto.response.TaskResponse
import com.example.demo.entity.Task
import com.example.demo.enums.TaskPriority
import com.example.demo.exception.NotFoundException
import com.example.demo.repository.DepartmentRepository
import com.example.demo.repository.TaskRepository
import com.example.demo.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository,
    private val departmentRepository: DepartmentRepository,
    private val authService: AuthService
) {

    fun getAll(): List<TaskResponse> {
        return taskRepository.findAll().map { toResponse(it) }
    }

    fun create(request: CreateTaskRequest, httpRequest: HttpServletRequest): TaskResponse {
        val currentUser = authService.getUserEntity(httpRequest)

        val assignee = request.assigneeId?.let {
            userRepository.findById(it).orElseThrow { NotFoundException("Assignee not found") }
        }

        val department = request.departmentId?.let {
            departmentRepository.findById(it).orElseThrow { NotFoundException("Department not found") }
        }

        val task = Task(
            title = request.title,
            description = request.description,
            author = currentUser,
            assignee = assignee,
            department = department,
            priority = TaskPriority.valueOf(request.priority.uppercase()),
            deadline = request.deadline?.let { LocalDateTime.parse(it) }
        )

        return toResponse(taskRepository.save(task))
    }

    private fun toResponse(task: Task): TaskResponse {
        return TaskResponse(
            id = task.id!!,
            title = task.title,
            description = task.description,
            priority = task.priority.name,
            deadline = task.deadline?.toString(),
            authorId = task.author?.id,
            authorName = task.author?.name,
            assigneeId = task.assignee?.id,
            assigneeName = task.assignee?.name,
            departmentId = task.department?.id,
            departmentName = task.department?.name
        )
    }
}