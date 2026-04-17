package com.example.demo.entity

import com.example.demo.enums.Role
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,

    @Column(unique = true, nullable = false)
    var email: String ,

    @Column(nullable = false)
    var password: String?,

    @Enumerated(EnumType.STRING)
    var role: Role = Role.USER,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    var department: Department? = null
)