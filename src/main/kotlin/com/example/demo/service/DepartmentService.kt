package com.example.demo.service

import com.example.demo.entity.Department
import com.example.demo.repository.DepartmentRepository
import org.springframework.stereotype.Service

@Service
class DepartmentService(
    private val departmentRepository: DepartmentRepository
) {

    fun getAll(): List<Department> {
        return departmentRepository.findAll()
    }

    fun create(name: String): Department {
        val department = Department(name = name)
        return departmentRepository.save(department)
    }
}