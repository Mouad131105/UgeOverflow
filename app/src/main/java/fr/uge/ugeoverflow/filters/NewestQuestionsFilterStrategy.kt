package fr.uge.ugeoverflow.filters

import android.util.Log
import fr.uge.ugeoverflow.api.OneQuestionResponse
import java.text.SimpleDateFormat
import java.util.*

class NewestQuestionsFilterStrategy : QuestionFilterStrategy {

    override fun getFilterName(): QuestionFilterType {
        return QuestionFilterType.NEWEST
    }

    override fun filterQuestions(questionList : List<OneQuestionResponse>): List<OneQuestionResponse> {
        Log.i("entered list size ", questionList.size.toString())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val newestQuestions =  questionList.sortedByDescending { dateFormat.parse(it.creationTime) }
        Log.i("newestQuestions", newestQuestions.size.toString())
        return newestQuestions

    }
    }