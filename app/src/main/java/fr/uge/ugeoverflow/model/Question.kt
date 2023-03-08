package fr.uge.ugeoverflow.model

import java.util.*

data class Question(
    private val id: UUID?,
    private val title: String?="",
    private val tags: Set<Tag>? = mutableSetOf(),
    private val votes: Set<Vote> = mutableSetOf(),
    private val content: Content?,
    private val answers: Set<Answer>?= mutableSetOf()
){
    val getId: UUID? get() = id
    val getTitle: String? get() = title
    val getTags: Set<Tag>? get() = tags
    val getVotes: Set<Vote> get() = votes
    val getContent: Content? get() = content
    val getAnswers: Set<Answer>? get() = answers

}

 class QuestionBuilder{

     private var id: UUID? = null
     private var title : String ?= ""
     private var tags: Set<Tag>? = mutableSetOf()
     private var votes: Set<Vote> = mutableSetOf()
     private var content: Content?=null
     private var answers: Set<Answer>?= mutableSetOf()

     fun id(id: UUID) = apply { this.id = id }
     fun title(title: String) = apply { this.title = title }
     fun tags(tags : Set<Tag>) = apply { this.tags = tags }
     fun votes(votes : Set<Vote>) = apply { this.votes = votes }
     fun content(content: Content) = apply { this.content = content }
     fun answers(answers: Set<Answer>) = apply { this.answers = answers }

     fun build(): Question {
         return Question(
             id = id, title = title, tags = tags, votes = votes,
         content = content, answers = answers
         )
     }

     companion object {
         fun builder() = QuestionBuilder()
     }
 }
