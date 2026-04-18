package com.example.demo.config

import com.example.demo.entity.Department
import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.repository.DepartmentRepository
import com.example.demo.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AdminInitializer {

    @Bean
    fun initAdmin(
        userRepository: UserRepository,
        departmentRepository: DepartmentRepository,
        passwordEncoder: PasswordEncoder
    ): CommandLineRunner {
        return CommandLineRunner {
            var adminDepartment = departmentRepository.findByName("Администрация")

            if (adminDepartment == null) {
                adminDepartment = departmentRepository.save(
                    Department(
                        name = "Администрация"
                    )
                )
            }

            val existing = userRepository.findByEmail("admin@gmail.com")

            if (existing == null) {
                val admin = User(
                    name = "Admin",
                    email = "admin@gmail.com",
                    password = passwordEncoder.encode("admin123"),
                    role = Role.ADMIN,
                    department = adminDepartment
                )
                userRepository.save(admin)
            } else {
                existing.department = adminDepartment
                userRepository.save(existing)
            }
        }
    }
}