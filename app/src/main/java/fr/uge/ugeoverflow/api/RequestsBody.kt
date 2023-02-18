package fr.uge.ugeoverflow.api

import fr.uge.ugeoverflow.model.Content
import fr.uge.ugeoverflow.model.Tag

data class RegisterRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val username: String,
    val password: String)

data class LoginRequest(val username: String, val password: String)

data class QuestionRequest(
    val title: String,
    val content: String,
    val tags: List<Tag>
)

data class ContentRequest (
    val text:String
    )



