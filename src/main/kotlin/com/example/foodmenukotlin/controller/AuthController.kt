package com.example.foodmenukotlin.controller


import com.example.foodmenukotlin.exception.AppException
import com.example.foodmenukotlin.model.Role
import com.example.foodmenukotlin.model.RoleName
import com.example.foodmenukotlin.model.User
import com.example.foodmenukotlin.payload.ApiResponse
import com.example.foodmenukotlin.payload.JwtAuthenticationResponse
import com.example.foodmenukotlin.payload.LoginRequest
import com.example.foodmenukotlin.payload.SignUpRequest
import com.example.foodmenukotlin.repository.RoleRepository
import com.example.foodmenukotlin.repository.UserRepository
import com.example.foodmenukotlin.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import javax.validation.Valid
import java.net.URI
import java.util.Collections


@RestController
@RequestMapping("/api/auth")
class AuthController {

    @Autowired
    internal var authenticationManager: AuthenticationManager? = null

    @Autowired
    internal var userRepository: UserRepository? = null

    @Autowired
    internal var roleRepository: RoleRepository? = null

    @Autowired
    internal var passwordEncoder: PasswordEncoder? = null

    @Autowired
    internal var tokenProvider: JwtTokenProvider? = null

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<*> {

        val authentication = authenticationManager!!.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.usernameOrEmail,
                        loginRequest.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val jwt = tokenProvider!!.generateToken(authentication)
        return ResponseEntity.ok(JwtAuthenticationResponse(jwt))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<*> {
        if (userRepository!!.existsByUsername(signUpRequest.username!!)!!) {
            return ResponseEntity(ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST)
        }

        if (userRepository!!.existsByEmail(signUpRequest.email!!)!!) {
            return ResponseEntity(ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST)
        }

        // Creating user's account
        val user = User(signUpRequest.name!!, signUpRequest.username!!,
                signUpRequest.email!!, signUpRequest.password!!)

        user.password = passwordEncoder!!.encode(user.password)

        val userRole = roleRepository!!.findByName(RoleName.ROLE_USER)
                .orElseThrow { AppException("User Role not set.") }

        user.roles = setOf(userRole)

        val result = userRepository!!.save(user)

        val location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.username!!).toUri()

        return ResponseEntity.created(location).body(ApiResponse(true, "User registered successfully"))
    }
}
