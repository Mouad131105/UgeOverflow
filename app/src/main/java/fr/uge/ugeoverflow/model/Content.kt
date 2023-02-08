package fr.uge.ugeoverflow.model

import java.util.*

data class Content(
    private val id : UUID,
    private val imagesReferences: MutableSet<Image> = mutableSetOf(),
    private val text : String,
    )