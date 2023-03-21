package fr.uge.ugeoverflow.filters

import fr.uge.ugeoverflow.api.OneQuestionResponse
import fr.uge.ugeoverflow.ui.screens.question.AllQuestionsScreen

class QuestionsFilterManager() {
    private lateinit var filterStrategies: Map<QuestionFilterType, QuestionFilterStrategy>
    fun init() {

        filterStrategies = mapOf(
            QuestionFilterType.NEWEST to NewestQuestionsFilterStrategy(),
            QuestionFilterType.OLDEST to OldestQuestionsFilterStrategy(),
            QuestionFilterType.UNANSWERED to UnansweredQuestionsFilterStrategy(),
            QuestionFilterType.ANSWERED to AnsweredQuestionsFilterStrategy(),
            QuestionFilterType.ALL to AllQuestionsFilterStrategy()
        )
    }
    fun  getQuestionsByFilter(filterName:String, filteredList : List<OneQuestionResponse> ) : List<OneQuestionResponse> {
         val filterStrategyChosen : QuestionFilterStrategy = filterStrategies[(QuestionFilterType.valueOf(filterName))]
             ?: throw  IllegalArgumentException("Invalid filter name: $filterName")
        return filterStrategyChosen.filterQuestions(filteredList)
    }
}