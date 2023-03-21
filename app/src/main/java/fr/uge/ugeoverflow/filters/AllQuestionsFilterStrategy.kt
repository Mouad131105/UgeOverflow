package fr.uge.ugeoverflow.filters

import android.util.Log
import fr.uge.ugeoverflow.api.OneQuestionResponse

class AllQuestionsFilterStrategy : QuestionFilterStrategy {

    override fun getFilterName(): QuestionFilterType {
        return QuestionFilterType.ALL
    }

    override fun filterQuestions(questionList: List<OneQuestionResponse>): List<OneQuestionResponse> {
        Log.i("All entered list size ", questionList.size.toString())
        return questionList
    }
}