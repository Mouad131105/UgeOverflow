package fr.uge.ugeoverflow.filters

import android.util.Log
import fr.uge.ugeoverflow.api.OneQuestionResponse

class UnansweredQuestionsFilterStrategy : QuestionFilterStrategy {

    override fun getFilterName(): QuestionFilterType {
        return QuestionFilterType.UNANSWERED
    }

    override fun filterQuestions(questionList : List<OneQuestionResponse>): List<OneQuestionResponse> {
        Log.i("unanswered entered list size ", questionList.size.toString())
        return questionList.filter { it.answers.isEmpty() }
    }
}