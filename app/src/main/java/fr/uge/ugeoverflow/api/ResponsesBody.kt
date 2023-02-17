package fr.uge.ugeoverflow.api

import java.util.UUID

data class QuestionResponse(val id:UUID, val title:String)

data class LoginResponse(
    val authentication: Authentication,
    val token: String
)

data class Authentication(
    val authorities: List<Authority>,
    val authenticated: Boolean,
    val principal: User,
    val credentials: Any?,
    val name: String
)

data class Authority(
    val authority: String
)

data class User(
    val id: String,
    val firstname: String,
    val lastname: String,
    val username: String,
    val email: String,
    val address: Any?,
    val role: String,
    val image: Any?,
    val followers: List<String>,
    val following: List<String>,
    val questions: List<String>,
    val token: String,
    val enabled: Boolean,
    val authorities: List<Authority>,
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val credentialsNonExpired: Boolean
)