package fr.uge.ugeoverflow.model

import java.util.UUID

data class User(
     val id: Int,
     val firstName: String,
     val lastName: String,
     val username: String,
     val email: String,
     val address: String,
     //val token : String

)