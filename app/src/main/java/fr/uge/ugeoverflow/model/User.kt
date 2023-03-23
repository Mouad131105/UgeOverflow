package fr.uge.ugeoverflow.model

import java.util.*

data class User(
     val id: UUID,
     val firstName: String,
     val lastName: String,
     val username: String,
     val email: String,
     val address: Address
){
     var score: Long? = null
     var image: Image? = null
}

class UserBuilder {
     private var id: UUID? = null
     private var firstName: String? = null
     private var lastName: String? = null
     private var username: String? = null
     private var email: String? = null
     private var address: Address? = null
     private var score: Long? = null

     fun setId(id: UUID) = apply { this.id = id }
     fun setFirstName(firstName: String) = apply { this.firstName = firstName }
     fun setLastName(lastName: String) = apply { this.lastName = lastName }
     fun setUsername(username: String) = apply { this.username = username }
     fun setEmail(email: String) = apply { this.email = email }
     fun setAddress(block: AddressBuilder.() -> Unit) {
          val builder = AddressBuilder.builder()
          block.invoke(builder)
          address = builder.build()
     }
     fun setScore(score: Long) = apply { this.score = score }

//     fun build(): User {
//          return User(
//               id ?: throw IllegalStateException("Id must be set"),
//               firstName ?: "",
//               lastName ?: "",
//               username ?: "",
//               email ?: "",
//               address ?: throw IllegalStateException("Address must be set")
//          ).apply { this.score = this@UserBuilder.score ?: 0 }
//     }
     companion object {
          fun builder() = UserBuilder()
     }
}

