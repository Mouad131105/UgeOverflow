package fr.uge.ugeoverflow.services

import fr.uge.ugeoverflow.api.QuestionRequest
import fr.uge.ugeoverflow.api.QuestionResponse
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import kotlinx.coroutines.runBlocking

object QuestionService {
    fun postQuestion(
        questionRequest: QuestionRequest,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) = runBlocking {
        val token = SessionManagerSingleton.sessionManager.getToken()
        val response = ApiService.init().postQuestion(
            "Bearer $token",
            questionRequest
        )
        if (response.isSuccessful) {
            onSuccess()
        } else {
            onError()
        }
    }

    fun getQuestions(
        onSuccess: (List<QuestionResponse>) -> Unit,
        onError: () -> Unit
    ) = runBlocking {
        val response = ApiService.init().getAllQuestions()
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            onError()
        }
    }

}