package fr.uge.ugeoverflow.api

import fr.uge.ugeoverflow.model.Location
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


//data class User(
//    val id: String,
//    val firstname: String,
//    val lastname: String,
//    val username: String,
//    val email: String,
//    val address: Any?,
//    val role: String,
//    val image: Any?,
//    val followers: List<String>,
//    val following: List<String>,
//    val questions: List<String>,
//    val token: String,
//    val enabled: Boolean,
//    val authorities: List<Authority>,
//    val accountNonExpired: Boolean,
//    val accountNonLocked: Boolean,
//    val credentialsNonExpired: Boolean
//)