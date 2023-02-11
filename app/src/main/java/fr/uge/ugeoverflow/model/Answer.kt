package fr.uge.ugeoverflow.model

import java.util.UUID

data class Answer(

    private val votes: MutableSet<Vote> = mutableSetOf(),
    private val content: Content,

)