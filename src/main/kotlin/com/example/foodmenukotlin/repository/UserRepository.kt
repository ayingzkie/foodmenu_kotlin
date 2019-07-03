package com.example.foodmenukotlin.repository

import com.example.foodmenukotlin.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID


@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): Optional<User>

    fun findByUsernameOrEmail(username: String, email: String): Optional<User>

    fun findByIdIn(userIds: List<Long>): List<User>

    fun findByUsername(username: String): Optional<User>

    fun existsByUsername(username: String): Boolean?

    fun existsByEmail(email: String): Boolean?
}
