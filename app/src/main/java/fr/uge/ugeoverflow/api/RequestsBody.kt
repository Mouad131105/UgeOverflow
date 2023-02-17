package fr.uge.ugeoverflow.api

data class RegisterRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val username: String,
    val password: String)

data class LoginRequest(val username: String, val password: String)

