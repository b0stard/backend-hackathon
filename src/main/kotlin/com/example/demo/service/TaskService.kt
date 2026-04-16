package com.example.demo.service

import com.example.demo.dto.request.CreateTaskRequest
import com.example.demo.dto.request.UpdateTaskAssigneeRequest
import com.example.demo.dto.request.UpdateTaskDeadlineRequest
import com.example.demo.dto.request.UpdateTaskStatusRequest
import com.example.demo.dto.response.CommentResponse
import com.example.demo.dto.response.CreateTaskMetaResponse
import com.example.demo.dto.response.DepartmentResponse
import com.example.demo.dto.response.TaskDetailsResponse
import com.example.demo.dto.response.TaskHistoryResponse
import com.example.demo.dto.response.TaskResponse
import com.example.demo.dto.response.UserShortResponse
import com.example.demo.entity.Task
import com.example.demo.entity.TaskHistory
import com.example.demo.enums.TaskPriority
import com.example.demo.enums.TaskStatus
import com.example.demo.exception.NotFoundException
import com.example.demo.repository.CommentRepository
import com.example.demo.repository.DepartmentRepository
import com.example.demo.repository.TaskHistoryRepository
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
    private val commentRepository: CommentRepository,
    private val taskHistoryRepository: TaskHistoryRepository,
    private val emailService: EmailService
) {

    fun getAllTasks(): List<TaskResponse> {
        return taskRepository.findAllWithRelations().map { it.toTaskResponse() }
    }

    fun getMyTasks(userId: Long): List<TaskResponse> {
        return taskRepository.findAllByAssigneeIdWithRelations(userId).map { it.toTaskResponse() }
    }

    fun getTasksByFilters(
        assigneeId: Long?,
        priority: TaskPriority?,
        title: String?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): List<TaskResponse> {
        return taskRepository.findByFilters(
            assigneeId = assigneeId,
            priority = priority,
            title = title,
            startDate = startDate,
            endDate = endDate
        ).map { it.toTaskResponse() }
    }

    fun getTaskById(id: Long): TaskDetailsResponse {
        val task = taskRepository.findByIdWithRelations(id)
            .orElseThrow { NotFoundException("Задача не найдена") }

        val comments = commentRepository.findAllByTaskIdWithAuthor(task.id).map {
            CommentResponse(
                id = it.id,
                authorId = it.author.id,
                authorName = it.author.name,
                text = it.text,
                createdAt = it.createdAt
            )
        }

        val history = taskHistoryRepository.findAllByTaskIdWithChangedByUser(task.id).map {
            TaskHistoryResponse(
                id = it.id,
                field = it.field,
                oldValue = it.oldValue,
                newValue = it.newValue,
                changedByUserId = it.changedByUser.id,
                changedByUserName = it.changedByUser.name,
                changedAt = it.changedAt
            )
        }

        return TaskDetailsResponse(
            id = task.id,
            title = task.title,
            description = task.description,
            status = task.status.name,
            priority = task.priority.name,
            deadline = task.deadline,
            createdAt = task.createdAt,
            author = UserShortResponse(
                id = task.author.id,
                name = task.author.name
            ),
            assignee = UserShortResponse(
                id = task.assignee.id,
                name = task.assignee.name
            ),
            department = DepartmentResponse(
                id = task.department.id,
                name = task.department.name
            ),
            isOverdue = task.isOverdue(),
            comments = comments,
            history = history
        )
    }
    fun getCreateTaskMeta(): CreateTaskMetaResponse {
        val users = userRepository.findAll().map {
            UserShortResponse(
                id = it.id,
                name = it.name
            )
        }

        val departments = departmentRepository.findAll().map {
            DepartmentResponse(
                id = it.id,
                name = it.name
            )
        }

        val priorities = com.example.demo.enums.TaskPriority.values()
            .map { it.name }

        val statuses = TaskStatus.values()
            .map { it.name }

        return CreateTaskMetaResponse(
            users = users,
            departments = departments,
            priorities = priorities,
            statuses = statuses
        )
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

        taskHistoryRepository.save(
            TaskHistory(
                task = savedTask,
                field = "created",
                oldValue = null,
                deadline = savedTask.deadline.toString(),
                newValue = "Задача создана",
                changedByUser = author
            )
        )

        emailService.sendTaskAssignedEmail(
            to = assignee.email,
            taskTitle = savedTask.title,
            deadline = savedTask.deadline
        )

        return taskRepository.findByIdWithRelations(savedTask.id)
            .orElseThrow { NotFoundException("Ошибка загрузки созданной задачи") }
            .toTaskResponse()
    }
    fun getTasks(
        assigneeId: Long?,
        priority: TaskPriority?,
        title: String?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): List<TaskResponse> {

        return taskRepository.findByFilters(
            assigneeId,
            priority,
            title,
            startDate,
            endDate
        ).map { it.toTaskResponse() }
    }

    @Transactional
    fun updateTaskStatus(
        taskId: Long,
        request: UpdateTaskStatusRequest,
        changedByUserId: Long
    ): TaskResponse {
        val task = taskRepository.findById(taskId)
            .orElseThrow { NotFoundException("Задача не найдена") }

        val changedBy = userRepository.findById(changedByUserId)
            .orElseThrow { NotFoundException("Пользователь не найден") }

        val oldStatus = task.status.name
        task.status = request.status
        task.updatedAt = LocalDateTime.now()

        val savedTask = taskRepository.save(task)

        taskHistoryRepository.save(
            TaskHistory(
                task = savedTask,
                field = "status",
                oldValue = oldStatus,
                newValue = request.status.name,
                changedByUser = changedBy
            )
        )

        return taskRepository.findByIdWithRelations(savedTask.id)
            .orElseThrow { NotFoundException("Ошибка загрузки задачи") }
            .toTaskResponse()
    }

    @Transactional
    fun updateTaskAssignee(
        taskId: Long,
        request: UpdateTaskAssigneeRequest,
        changedByUserId: Long
    ): TaskResponse {
        val task = taskRepository.findById(taskId)
            .orElseThrow { NotFoundException("Задача не найдена") }

        val newAssignee = userRepository.findById(request.assigneeId)
            .orElseThrow { NotFoundException("Исполнитель не найден") }

        val changedBy = userRepository.findById(changedByUserId)
            .orElseThrow { NotFoundException("Пользователь не найден") }

        val oldAssigneeName = task.assignee.name
        task.assignee = newAssignee
        task.updatedAt = LocalDateTime.now()

        val savedTask = taskRepository.save(task)

        taskHistoryRepository.save(
            TaskHistory(
                task = savedTask,
                field = "assignee",
                oldValue = oldAssigneeName,
                newValue = newAssignee.name,
                changedByUser = changedBy
            )
        )

        return taskRepository.findByIdWithRelations(savedTask.id)
            .orElseThrow { NotFoundException("Ошибка загрузки задачи") }
            .toTaskResponse()
    }

    @Transactional
    fun updateTaskDeadline(
        taskId: Long,
        request: UpdateTaskDeadlineRequest,
        changedByUserId: Long
    ): TaskResponse {
        val task = taskRepository.findById(taskId)
            .orElseThrow { NotFoundException("Задача не найдена") }

        val changedBy = userRepository.findById(changedByUserId)
            .orElseThrow { NotFoundException("Пользователь не найден") }

        val oldDeadline = task.deadline.toString()
        task.deadline = request.deadline
        task.updatedAt = LocalDateTime.now()

        val savedTask = taskRepository.save(task)

        taskHistoryRepository.save(
            TaskHistory(
                task = savedTask,
                field = "deadline",
                oldValue = oldDeadline,
                newValue = request.deadline.toString(),
                changedByUser = changedBy
            )
        )

        return taskRepository.findByIdWithRelations(savedTask.id)
            .orElseThrow { NotFoundException("Ошибка загрузки задачи") }
            .toTaskResponse()
    }

    private fun Task.toTaskResponse(): TaskResponse {
        return TaskResponse(
            id = this.id,
            title = this.title,
            shortDescription = this.description,
            status = this.status.name,
            priority = this.priority.name,
            deadline = this.deadline,
            createdAt = this.createdAt,
            authorId = this.author.id,
            authorName = this.author.name,
            assigneeId = this.assignee.id,
            assigneeName = this.assignee.name,
            isOverdue = this.isOverdue()
        )
    }

    private fun Task.isOverdue(): Boolean {
        return this.status != TaskStatus.DONE && this.deadline.isBefore(LocalDateTime.now())
    }
}