package fr.uge.ugeoverflow.services

import fr.uge.ugeoverflow.api.CommentRequest
import fr.uge.ugeoverflow.api.OneQuestionResponse
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import kotlinx.coroutines.runBlocking

object CommentService {

    fun addComment(
        commentRequest: CommentRequest,
        questionId: String,
        answerId: String?,
        onSuccess: (OneQuestionResponse) -> Unit,
        errorCallback: () -> Unit
    ) = runBlocking {

        val token = SessionManagerSingleton.sessionManager.getToken()
        if (!answerId.isNullOrBlank()) {
            val response = ApiService.init().postCommentForAnswer(
                "Bearer $token",
                questionId,
                answerId.toString(),
                commentRequest
            )
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
            } else {
                errorCallback()
            }
        } else {
            val response = ApiService.init()
                .postCommentForQuestion("Bearer $token", questionId, commentRequest)
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
            } else {
                errorCallback()
            }
        }

    }
}