package fr.uge.ugeoverflow.api

import fr.uge.ugeoverflow.model.MyLocation

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
    val myLocation: MyLocation?
)

data class ContentRequest(
    val text: String
)



