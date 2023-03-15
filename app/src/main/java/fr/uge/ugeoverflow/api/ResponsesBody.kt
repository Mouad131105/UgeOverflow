package fr.uge.ugeoverflow.api

import java.util.*

data class QuestionResponse(
    val id: UUID,
    val title: String,
    val body: String,
    val tags: List<String>,
    val userId: UUID,
    val creationTime: String
)

data class LoginResponse(
    val message: String,
    val data: String
)

data class TagsResponse(
    val tags: List<String>
)

data class userBoxResponse(
    val id: UUID,
    val username:String,
    val email:String,
    val profilePicture:String,
)


data class CommentResponse(
    val id: String,
    val body: String,
    val user: userBoxResponse,
    val creationTime: String
)
data class OneQuestionResponse(
    val id: String,
    val title: String,
    val body: String,
    val tags: List<String>,
    val user: userBoxResponse,
    val creationTime: String,
    val answersCounter: Int,
    val comments: List<CommentResponse>,
    val answers: List<AnswerResponse>
)
data class AnswerResponse(
    val id: String,
    val body: String,
    val user: userBoxResponse,
    val creationTime: String,
    val score: Int,
    val votes: List<String>,
    val comments: List<CommentResponse>
)
