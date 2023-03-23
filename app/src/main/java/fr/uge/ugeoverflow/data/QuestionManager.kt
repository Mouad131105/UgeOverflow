package fr.uge.ugeoverflow.data

import android.util.Log
import fr.uge.ugeoverflow.api.OneQuestionResponse
import fr.uge.ugeoverflow.session.ApiService
import kotlinx.coroutines.runBlocking

object QuestionManager {

    fun getQuestionById(questionId: String): OneQuestionResponse = runBlocking {
        val response = ApiService.init().getQuestion(questionId)
        Log.d("response ", response.message())
        if (response.isSuccessful){
            Log.d("response ", response.message())
        }
        response.body() ?: throw RuntimeException("Failed to fetch question Do")
    }
}