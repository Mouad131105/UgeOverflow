package fr.uge.ugeoverflow.filters

enum class QuestionFilterType(private val filterName: String) {
        NEWEST("NEWEST"),
        OLDEST("OLDEST"),
        UNANSWERED("UNANSWERED"),
        ANSWERED("ANSWERED"),
        ALL("ALL");
        fun getFilterName(): String {
                return filterName
        }
}