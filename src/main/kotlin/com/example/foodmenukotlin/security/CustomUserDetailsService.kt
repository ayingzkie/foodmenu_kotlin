package com.example.foodmenukotlin.security

import com.example.foodmenukotlin.exception.ResourceNotFoundException
import com.example.foodmenukotlin.model.User
import com.example.foodmenukotlin.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.UUID


@Service
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    internal var userRepository: UserRepository? = null

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(usernameOrEmail: String): UserDetails {
        // Let people login with either username or email
        val user = userRepository!!.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow { UsernameNotFoundException("User not found with username or email : $usernameOrEmail") }

        return UserPrincipal.create(user)
    }

    @Transactional
    fun loadUserById(id: UUID): UserDetails {
        val user = userRepository!!.findById(id).orElseThrow { ResourceNotFoundException("User", "id", id) }

        return UserPrincipal.create(user)
    }
}