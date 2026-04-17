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
        val department = Department(
            name = name
        )
        return departmentRepository.save(department)
    }

    fun update(id: Long, name: String): Department {
        val dep = departmentRepository.findById(id)
            .orElseThrow { RuntimeException("Department not found") }

        dep.name = name
        return departmentRepository.save(dep)
    }

    fun delete(id: Long) {
        departmentRepository.deleteById(id)
    }
}