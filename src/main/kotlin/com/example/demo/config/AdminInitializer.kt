package com.example.demo.config

import com.example.demo.entity.User
import com.example.demo.enums.Role
import com.example.demo.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AdminInitializer {
private val logger = LoggerFactory.getLogger(this.javaClass)
    @Bean
    fun initAdmin(
        userRepository: UserRepository,
        passwordEncoder: PasswordEncoder
    ): CommandLineRunner {
        return CommandLineRunner {

            val existing = userRepository.findByEmail("admin@gmail.com")

            if (existing == null) {
                val admin = User(
                    email = "admin@gmail.com",
                    password = passwordEncoder.encode("admin123"),
                    name = "Admin",
                    role = Role.ADMIN,
                    department = null
                )

                userRepository.save(admin)

                logger.info("ADMIN CREATED: admin@gmail.com / admin123")
            } else {
                logger.info("ADMIN ALREADY EXISTS")
            }
        }
    }
}