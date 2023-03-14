package fr.uge.ugeoverflow.api

import fr.uge.ugeoverflow.model.Location

data class RegisterRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val username: String,
    val password: String
)

data class LoginRequest(val username: String, val password: String)

data class QuestionRequest(
    val title: String,
    val body: String,
    val tags: List<String>,
    val location: Location
)

data class ContentRequest(
    val text: String
)


