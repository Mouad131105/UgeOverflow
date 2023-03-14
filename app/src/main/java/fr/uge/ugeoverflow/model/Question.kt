package fr.uge.ugeoverflow.model

import java.util.*

data class Question(
    private val id: UUID?,
    private val title: String? = "",
    private val tags: Set<String>? = mutableSetOf(),
    private val votes: Set<Vote> = mutableSetOf(),
    private val body: String?,
    private val answers: Set<Answer>? = mutableSetOf(),
    private val location: Location = Location(0.0, 0.0),
    private val creationTime: String = "",
) {
    val getId: UUID? get() = id
    val getTitle: String? get() = title
    val getTags: Set<String>? get() = tags
    val getVotes: Set<Vote> get() = votes
    val getBody: String? get() = body
    val getAnswers: Set<Answer>? get() = answers
    val getLocation: Location? get() = location
    val getCreationTime: String? get() = creationTime
}

data class Location(
    private val latitude: Double,
    private val longitude: Double
) {
    val getLatitude: Double get() = latitude
    val getLongitude: Double get() = longitude
}

class QuestionBuilder {

    private var id: UUID? = null
    private var title: String? = ""
    private var tags: Set<String>? = mutableSetOf()
    private var votes: Set<Vote> = mutableSetOf()
    private var body: String? = null
    private var answers: Set<Answer>? = mutableSetOf()


    fun id(id: UUID) = apply { this.id = id }
    fun title(title: String) = apply { this.title = title }
    fun tags(tags: Set<String>) = apply { this.tags = tags }
    fun votes(votes: Set<Vote>) = apply { this.votes = votes }
    fun content(content: String) = apply { this.body = content }
    fun answers(answers: Set<Answer>) = apply { this.answers = answers }

    fun build(): Question {
        return Question(
            id = id, title = title, tags = tags, votes = votes,
            body = body, answers = answers
        )
    }

    companion object {
        fun builder() = QuestionBuilder()
    }
}
