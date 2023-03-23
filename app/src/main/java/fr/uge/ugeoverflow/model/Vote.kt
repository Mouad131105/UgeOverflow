package fr.uge.ugeoverflow.model


data class Vote(
    private val vote_type: VOTE_TYPE,
    private val user: User
)