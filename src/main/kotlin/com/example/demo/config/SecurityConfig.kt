package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .cors { }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authorizeHttpRequests {
                it.anyRequest().permitAll()
            }
            .build()
    }


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()

        config.allowedOrigins = listOf(
            "https://qitask.vercel.app"
        )

        config.allowedMethods = listOf(
            "GET", "POST", "PUT", "DELETE", "OPTIONS"
        )

        config.allowedHeaders = listOf("*")

        config.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)

        return source
    }
}