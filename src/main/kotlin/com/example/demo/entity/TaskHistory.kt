package com.example.demo.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "task_history")
data class TaskHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    var task: Task,

    @Column(nullable = false)
    var field: String,
    var deadline: String? = null,

    @Column(name = "old_value", columnDefinition = "text")
    var oldValue: String? = null,

    @Column(name = "new_value", columnDefinition = "text")
    var newValue: String? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "changed_by_user_id", nullable = false)
    var changedByUser: User,

    @Column(name = "changed_at", nullable = false)
    var changedAt: LocalDateTime = LocalDateTime.now()
)