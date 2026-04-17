package com.example.demo.entity

import com.example.demo.enums.TaskPriority
import com.example.demo.enums.TaskStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var title: String,

    @Column(columnDefinition = "text")
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TaskStatus = TaskStatus.NEW,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var priority: TaskPriority,

    @Column(nullable = false)
    var deadline: LocalDateTime? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    var author: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = false)
    var assignee: User? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    var department: Department? = null
)