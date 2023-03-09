package fr.uge.ugeoverflow.model

import java.util.*

data class Tag(
    private val id :Long,
    private val tag_type : String,
    private val description : String
){
    val getTAG_TYPE: String? get() = tag_type
    val getDESCRIPTION: String? get() = description
}