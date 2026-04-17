package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "departments")
class Department(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String = ""
)