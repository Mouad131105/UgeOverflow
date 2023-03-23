package fr.uge.ugeoverflow.services

import fr.uge.ugeoverflow.api.OneQuestionResponse
import fr.uge.ugeoverflow.session.ApiService
import kotlinx.coroutines.runBlocking

object AnswerService {

    fun addAnswer(
        answerRequest: fr.uge.ugeoverflow.api.AnswerRequest,
        overflowId: String,
        onSuccess: (OneQuestionResponse) -> Unit,
        errorCallback: () -> Unit
    ) = kotlinx.coroutines.runBlocking {
        val token = fr.uge.ugeoverflow.session.SessionManagerSingleton.sessionManager.getToken()
        val response = fr.uge.ugeoverflow.session.ApiService.init().postAnswer("Bearer $token",overflowId, answerRequest)
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            errorCallback()
        }
    }

    fun deleteAnswer(
        onSuccess: (OneQuestionResponse) -> Unit,
        errorCallback: () -> Unit,
    ) = runBlocking{
        val token = fr.uge.ugeoverflow.session.SessionManagerSingleton.sessionManager.getToken()
        val response = ApiService.init().deleteAnswer("Bearer $token")
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            errorCallback()
        }

    }

    fun voteUp(id: String, function: () -> Unit, function1: () -> Unit) {

    }

    fun voteDown(id: String, function: () -> Unit, function1: () -> Unit) {

    }
}