package com.example.demo.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService(
    @Value("\${jwt.secret}")
    private val secret: String
) {

    private val expiration = 1000 * 60 * 60 * 24 // 1 день

    fun generateToken(email: String): String {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS256, secret.toByteArray())
            .compact()
    }

    fun extractEmail(token: String): String {
        return Jwts.parser()
            .setSigningKey(secret.toByteArray())
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun isValid(token: String): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(secret.toByteArray())
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }
}