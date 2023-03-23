package fr.uge.ugeoverflow.model

data class Tag(
    private val id: Long,
    private val tag_type: String,
    private val description: String
) {
    val getTAG_TYPE: String? get() = tag_type
    val getDescription: String? get() = description
}