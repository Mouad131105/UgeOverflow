package fr.uge.ugeoverflow.filters

import fr.uge.ugeoverflow.api.OneQuestionResponse


interface QuestionFilterStrategy {
    fun getFilterName(): QuestionFilterType
    fun filterQuestions(list : List<OneQuestionResponse> ): List<OneQuestionResponse>
}