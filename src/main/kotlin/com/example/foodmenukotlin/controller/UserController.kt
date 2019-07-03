package com.example.foodmenukotlin.controller


import com.example.foodmenukotlin.exception.ResourceNotFoundException
import com.example.foodmenukotlin.payload.*
import com.example.foodmenukotlin.repository.UserRepository
import com.example.foodmenukotlin.security.UserPrincipal
import com.example.foodmenukotlin.security.CurrentUser
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController {

    @Autowired
    private val userRepository: UserRepository? = null

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    fun getCurrentUser(@CurrentUser currentUser: UserPrincipal): UserSummary {
        return UserSummary(currentUser.id, currentUser.username, currentUser.name)
    }

    @GetMapping("/user/checkUsernameAvailability")
    fun checkUsernameAvailability(@RequestParam(value = "username") username: String): UserIdentityAvailability {
        val isAvailable = (!userRepository!!.existsByUsername(username)!!)!!
        return UserIdentityAvailability(isAvailable)
    }

    @GetMapping("/user/checkEmailAvailability")
    fun checkEmailAvailability(@RequestParam(value = "email") email: String): UserIdentityAvailability {
        val isAvailable = (!userRepository!!.existsByEmail(email)!!)!!
        return UserIdentityAvailability(isAvailable)
    }

    @GetMapping("/users/{username}")
    fun getUserProfile(@PathVariable(value = "username") username: String): UserProfile {
        val user = userRepository!!.findByUsername(username)
                .orElseThrow { ResourceNotFoundException("User", "username", username) }

        //        long pollCount = pollRepository.countByCreatedBy(user.getId());
        //        long voteCount = voteRepository.countByUserId(user.getId());

        return UserProfile(user.id, user.username, user.name, user.createdAt, 0, 0)
    }

    companion object {

        private val logger = LoggerFactory.getLogger(UserController::class.java)
    }


}
