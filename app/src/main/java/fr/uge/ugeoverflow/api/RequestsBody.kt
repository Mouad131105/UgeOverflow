package fr.uge.ugeoverflow.api

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
    val tags: List<String>
)

data class ContentRequest(
    val text: String
)



