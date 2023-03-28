package fr.uge.ugeoverflow.api


import android.location.Location
import fr.uge.ugeoverflow.model.MyLocation
import fr.uge.ugeoverflow.model.VOTE_TYPE
import java.text.SimpleDateFormat
import java.util.*

data class QuestionResponse(
    val id: UUID,
    val title: String,
    val body: String,
    val tags: List<String>,
    val user: UserBoxDTO,
    val creationTime: String,
    val myLocation: MyLocation
)


data class LoginResponse(
    val user: UserBoxDTO,
    val token: String
)

data class TagsResponse(
    val tags: List<String>
)

data class TagResponse(
    val tagType: String,
    val description: String,
    val questionCount: Int
)


data class AddressDTO(
    val street: String?,
    val city: String?,
    val country: String?,
    val zipCode: String?
) {
    companion object {
        fun fromAddress(address: String?): AddressDTO {
            //every part is divided by a new line
            val parts = address?.split("\n") ?: return AddressDTO(null, null, null, null)
            return AddressDTO(
                parts?.get(0),
                parts?.get(1),
                parts?.get(2),
                parts?.get(3)
            )
        }
    }

    override fun toString(): String {
        return "$street\n $city\n $country\n $zipCode"
    }


}

data class UserBoxDTO(
    val id: UUID,
    val username: String,
    val email: String,
    val profilePicture: String,
    val address: AddressDTO
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
    val followers: List<UserBoxDTO>?,
    val followed: List<UserBoxDTO>?,
    val questions: List<QuestionResponse>?,
    val answers: List<AnswerDTO>?,
    val tags: Map<String, Int>?
)

class UpdateProfileDTO(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val bio: String?,
    val address: String?,
    val profilePicture: String?
)

class AnswerDTO(
    val id: UUID,
    val body: String,
    val user: UserBoxDTO,
    val creationTime: String,
    val score: Int = 0,
    val votes: List<VoteDTO>?,
    val comments: List<CommentDTO>?,
    val location: Location?
)

class VoteDTO(
    val vote: VOTE_TYPE,
    val user: UserBoxDTO
)


class CommentDTO(
    val id: UUID,
    val body: String,
    val user: UserBoxDTO,
    val creationTime: String,
)

data class CommentResponse(
    val id: String,
    val body: String,
    val user: UserBoxDTO,
    val creationTime: String
)

data class OneQuestionResponse(
    val id: String,
    val title: String,
    var body: String,
    val tags: List<String>,
    val user: UserBoxDTO,
    val creationTime: String,
    val answersCounter: Int,
    val comments: List<CommentResponse>,
    val answers: List<AnswerResponse>,
    val location: MyLocation
) {
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
    val user: UserBoxDTO,
    val creationTime: String,
    val score: Int,
    val votes: List<VoteResponse>,
    val comments: List<CommentResponse>,
    val upVotedByUser: Boolean,
    val downVotedByUser: Boolean
)

data class VoteResponse(
    val vote: String,
    val user: UserBoxDTO,
    val downvote: Boolean,
    val upvote: Boolean
)

data class ImageResponse(
    val id: String,
    val name: String,
    val data: ByteArray
)