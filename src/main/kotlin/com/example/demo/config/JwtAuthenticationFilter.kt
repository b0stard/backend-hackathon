package com.example.demo.config

import com.example.demo.repository.UserRepository
import com.example.demo.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.cookies
            ?.firstOrNull { it.name == "jwt" }
            ?.value

        if (token.isNullOrBlank()) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val email = jwtService.extractEmail(token)

            if (SecurityContextHolder.getContext().authentication == null) {
                val user = userRepository.findByEmail(email).orElse(null)

                if (user != null && jwtService.isTokenValid(token, user.email)) {
                    val authorities = listOf(
                        SimpleGrantedAuthority("ROLE_${user.role.name}")
                    )

                    val authentication = UsernamePasswordAuthenticationToken(
                        user.email,
                        null,
                        authorities
                    )

                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (_: Exception) {
        }

        filterChain.doFilter(request, response)
    }
}