package com.example.demo.config

import com.example.demo.entity.User
import com.example.demo.enums.Role
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
        passwordEncoder: PasswordEncoder
    ): CommandLineRunner {
        return CommandLineRunner {

            val existing = userRepository.findByEmail("admin@mail.com")

            if (existing == null) {
                val admin = User(
                    email = "admin@mail.com",
                    password = passwordEncoder.encode("admin123"),
                    name = "Admin",
                    role = Role.ADMIN,
                    department = null
                )

                userRepository.save(admin)

                println("ADMIN CREATED: admin@mail.com / admin123")
            } else {
                println("ADMIN ALREADY EXISTS")
            }
        }
    }
}