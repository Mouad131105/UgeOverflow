package fr.uge.ugeoverflow.model

import java.util.UUID

data class Answer(
    private val id : UUID,
    private val question: Question,
    private val votes: MutableSet<Vote> = mutableSetOf(),
    private val content: Content,

)