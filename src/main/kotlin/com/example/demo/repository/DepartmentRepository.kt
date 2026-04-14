package com.example.demo.repository

import com.example.demo.entity.Department
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface DepartmentRepository : JpaRepository<Department, Long> {
    fun findByName(name: String): Optional<Department>
    fun existsByName(name: String): Boolean
}