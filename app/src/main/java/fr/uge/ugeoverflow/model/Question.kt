package fr.uge.ugeoverflow.model

import java.util.*

data class Question(
    private val id: UUID?,
    private val title : String?="",
    private val tags: Set<Tag>? = mutableSetOf(),
    private val votes: Set<Vote>? = mutableSetOf(),
    private val content: Content?,
    private val description: String?,
){
    val getId: UUID? get() = id
    val getTitle: String? get() = title
    val getTags: Set<Tag>? get() = tags
    val getVotes: Set<Vote>? get() = votes
    val getContent: Content? get() = content
    val getDescription: String? get() = description
}

 class QuestionBuilder{

     private var id: UUID? = null
     private var title : String ?= ""
     private var tags: Set<Tag>? = mutableSetOf()
     private var votes: Set<Vote>? = mutableSetOf()
     private var content: Content?=null
     private var description: String?= ""



     fun id(id: UUID) = apply { this.id = id }
     fun title(title: String) = apply { this.title = title }
     fun tags(tags : Set<Tag>) = apply { this.tags = tags }
     fun votes(votes : Set<Vote>) = apply { this.votes = votes }
     fun content(email: Content) = apply { this.content = content }
     fun description(description: String) = apply { this.description = description }

     fun build(): Question {
         return Question(id = id, title = title, tags = tags, votes = votes,
         content = content, description = description
         )
     }

     companion object {
         fun builder() = QuestionBuilder()
     }
 }
