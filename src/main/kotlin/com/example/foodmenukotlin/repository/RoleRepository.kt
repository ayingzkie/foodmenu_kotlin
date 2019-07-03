package com.example.foodmenukotlin.repository

import com.example.foodmenukotlin.model.Role
import com.example.foodmenukotlin.model.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.util.Optional


@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(roleName: RoleName): Optional<Role>
}
