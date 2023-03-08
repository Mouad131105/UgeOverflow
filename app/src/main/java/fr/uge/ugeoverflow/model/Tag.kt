package fr.uge.ugeoverflow.model

import java.util.*

data class Tag(
    private val id : UUID,
    private val tag_type : TAG_TYPE,
    private val description: String
){
    val getTAG_TYPE: TAG_TYPE? get() = tag_type
    val getDescription: String? get() = description
}