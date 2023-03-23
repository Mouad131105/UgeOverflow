package fr.uge.ugeoverflow.api


import fr.uge.ugeoverflow.model.User
import android.location.Location
import fr.uge.ugeoverflow.model.MyLocation
import fr.uge.ugeoverflow.model.VOTE_TYPE
import java.time.LocalDateTime
import java.util.*

data class QuestionResponse(
    val id: UUID,
    val title: String,
    val body: String,
    val tags: List<String>,
    val user: UserBoxResponse,
    val creationTime: String,
    val myLocation: MyLocation
)

data class LoginResponse(
    val user: UserBoxResponse,
    val token: String
)

data class TagsResponse(
    val tags: List<String>
)

data class UserBoxResponse(
    val id: UUID,
    val username: String,
    val email: String,
    val profilePicture: String,
)

data class UserProfileResponse(
    val id: String,
    val firstname: String,
    val lastname: String,
    val username: String,
    val email: String,
    val bio: String,
    val address: Any?,
    val role: String,
    val image: Any?,
    val followers: List<String>,
    val following: List<String>,
    val questions: List<String>,

    )

class UserProfileDTO(
    val firstName: String,
    val lastName: String,
    var username: String,
    val email: String,
    val bio: String?,
    val address: String?,
    val profilePicture: String?,
    val reputation: Int = 0,
    var creationTime: String?,
    val followers: List<UserBoxResponse>?,
    val followed: List<UserBoxResponse>?,
    val questions: List<QuestionResponse>?,
    val answers: List<AnswerDTO>?,
    val tags: Map<String, Int>?
)

class AnswerDTO(
    val id: UUID,
    val body: String,
    val user: UserBoxResponse,
    val creationTime: String,
    val score: Int = 0,
    val votes: List<VoteDTO>?,
    val comments: List<CommentDTO>?,
    val location: Location?
)

class VoteDTO(
    val vote: VOTE_TYPE,
    val user: UserBoxResponse
)


class CommentDTO(
    val id: UUID,
    val body: String,
    val user: UserBoxResponse,
    val creationTime: String,
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
)
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
