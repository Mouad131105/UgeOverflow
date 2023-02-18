package fr.uge.ugeoverflow.model

import java.util.*

data class Tag(
    private val tagType: String
){
    val getTag: String? get() = tagType
}