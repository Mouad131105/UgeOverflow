package fr.uge.ugeoverflow.filters

import android.util.Log
import fr.uge.ugeoverflow.api.OneQuestionResponse

class AnsweredQuestionsFilterStrategy : QuestionFilterStrategy {

    override fun getFilterName(): QuestionFilterType {
        return QuestionFilterType.ANSWERED
    }

    override fun filterQuestions(questionList : List<OneQuestionResponse>): List<OneQuestionResponse> {
        Log.i("entered list size ", questionList.size.toString())
        return questionList.filter { it.answers.isNotEmpty() }
    }
}