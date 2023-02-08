package fr.uge.ugeoverflow.model

import java.util.*

data class Address(
    private val id : UUID,
    private val  street : String,
    private val  city : String,
    private val  country : String,
    private val zipCode : String

)