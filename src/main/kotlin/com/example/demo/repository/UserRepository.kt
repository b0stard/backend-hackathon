package com.example.demo.repository

import com.example.demo.entity.User
import com.example.demo.enums.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean

    fun findAllByDepartmentId(departmentId: Long): List<User>

    fun findAllByRole(role: Role): List<User>

    @Query(
        """
        select u
        from User u
        left join fetch u.department
        where u.id = :id
        """
    )
    fun findByIdWithDepartment(@Param("id") id: Long): Optional<User>
}