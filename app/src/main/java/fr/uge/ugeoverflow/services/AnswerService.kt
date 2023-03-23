package fr.uge.ugeoverflow.services

import fr.uge.ugeoverflow.api.OneQuestionResponse

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
}