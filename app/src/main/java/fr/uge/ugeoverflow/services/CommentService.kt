package fr.uge.ugeoverflow.services

import fr.uge.ugeoverflow.api.CommentRequest
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import kotlinx.coroutines.runBlocking

object CommentService {
    fun addComment(
        commentRequest: CommentRequest,
        overflowId: String,
        successCallback: () -> Unit,
        errorCallback: () -> Unit
    ) = runBlocking {
        val token = SessionManagerSingleton.sessionManager.getToken()
        val response = ApiService.init().postComment("Bearer $token",overflowId, commentRequest)
        if (response.isSuccessful) {
            successCallback()
        } else {
            errorCallback()
        }
    }
}