package fr.uge.ugeoverflow.filters

import android.util.Log
import fr.uge.ugeoverflow.api.OneQuestionResponse
import java.text.SimpleDateFormat
import java.util.*

class OldestQuestionsFilterStrategy : QuestionFilterStrategy {

    override fun getFilterName(): QuestionFilterType {
        return QuestionFilterType.OLDEST
    }

    override fun filterQuestions(questionList : List<OneQuestionResponse>): List<OneQuestionResponse> {
        Log.i("entered list size ", questionList.size.toString())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val oldestQuestions =  questionList.sortedBy { dateFormat.parse(it.creationTime) }
        Log.i("oldest", oldestQuestions.size.toString())
        return oldestQuestions
    }

}