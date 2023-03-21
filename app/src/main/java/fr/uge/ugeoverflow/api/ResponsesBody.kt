package fr.uge.ugeoverflow.api

import fr.uge.ugeoverflow.model.Location
import fr.uge.ugeoverflow.model.User
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

data class QuestionResponse(
    val id: UUID,
    val title: String,
    val body: String,
    val tags: List<String>,
    val user: UserBoxResponse,
    val creationTime: String,
    val location: Location
)


data class LoginResponse(
    val message: String,
    val data: String
)

data class TagsResponse(
    val tags: List<String>
)

data class UserBoxResponse(
    val id: UUID,
    val username:String,
    val email:String,
    val profilePicture:String,
)


data class CommentResponse(
    val id: String,
    val body: String,
    val user: UserBoxResponse,
    val creationTime: String
)
data class OneQuestionResponse(
    val id: String,
    val title: String,
    val body: String,
    val tags: List<String>,
    val user: UserBoxResponse,
    val creationTime: String,
    val answersCounter: Int,
    val comments: List<CommentResponse>,
    val answers: List<AnswerResponse>,
    val location: Location
    ){
    fun getTimePassedSinceQuestionCreation(creationTime: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val date = dateFormat.parse(creationTime)
        val now = Date()

        val seconds = ((now.time - date.time) / 1000).toInt()
        val minutesPassed = seconds / 60
        val hoursPassed = minutesPassed / 60
        val daysPassed = hoursPassed / 24

        return when {
            minutesPassed == 0 -> "just now"
            minutesPassed == 1 -> "1 minute ago"
            minutesPassed < 60 -> "$minutesPassed minutes ago"
            hoursPassed == 1 -> "1 hour ago"
            hoursPassed < 24 -> "$hoursPassed hours ago"
            daysPassed == 1 -> "1 day ago"
            else -> "$daysPassed days ago"
        }
    }
}


data class AnswerResponse(
    val id: String,
    val body: String,
    val user: UserBoxResponse,
    val creationTime: String,
    val score: Int,
    val votes: List<VoteResponse>,
    val comments: List<CommentResponse>,
    val upVotedByUser:Boolean,
    val downVotedByUser:Boolean
)

data class VoteResponse(
    val vote: String,
    val user : UserBoxResponse,
    val downvote : Boolean,
    val upvote : Boolean
)
