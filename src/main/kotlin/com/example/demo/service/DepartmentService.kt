package com.example.demo.service

import com.example.demo.dto.response.DepartmentResponse
import com.example.demo.entity.Department
import com.example.demo.repository.DepartmentRepository
import org.springframework.stereotype.Service

@Service
class DepartmentService(
    private val departmentRepository: DepartmentRepository
) {

    fun getAllDepartments(): List<Department> {
        return departmentRepository.findAll()
    }

    fun getDepartmentById(id: Long): Department? {
        return departmentRepository.findById(id).orElse(null)
    }

    fun getAll(): List<DepartmentResponse> {
        return departmentRepository.findAll().map { dept ->
            DepartmentResponse(
                id = dept.id,
                name = dept.name
            )
        }
    }
}