package fr.uge.ugeoverflow.model

import java.util.UUID

data class User(
    private val id : UUID,
    private val firstName : String,
    private val lastName : String,
    private val username : String,
    private val email : String,
    private val address : Address,
)