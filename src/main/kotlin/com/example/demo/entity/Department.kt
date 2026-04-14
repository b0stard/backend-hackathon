package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "departments")
data class Department(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(columnDefinition = "text")
    val description: String? = null
)