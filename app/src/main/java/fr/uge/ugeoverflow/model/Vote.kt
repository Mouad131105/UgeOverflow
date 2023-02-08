package fr.uge.ugeoverflow.model

import java.util.UUID

data class Vote(
    private val id : UUID,
    private val vote_type: VOTE_TYPE,
    private val user : User
)