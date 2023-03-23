package fr.uge.ugeoverflow.model

data class Answer(

    private val votes: MutableSet<Vote> = mutableSetOf(),
    private val body: String,

    )