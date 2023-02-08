package fr.uge.ugeoverflow.model

import java.util.*

data class Question(
    private val id: UUID,
    private val title : String,
    private val tags: MutableSet<Tag> = mutableSetOf(),
    private val votes: MutableSet<Vote> = mutableSetOf(),
    private val content: Content,
    private val description: String,
)