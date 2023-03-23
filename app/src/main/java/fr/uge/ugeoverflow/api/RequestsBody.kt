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
    val location: MyLocation?
)

data class AnswerRequest(
    val body: String,
    //val location: Location
    val questionId: String
)

data class CommentRequest(
    val body: String,
    val questionId: String?,
    val answerId: String?
)


data class ReputationRequest(
    val username: String,
    val note: Int
)


data class ContentRequest(
    val text: String
)



