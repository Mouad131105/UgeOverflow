package fr.uge.ugeoverflow.services

import fr.uge.ugeoverflow.api.LoginRequest
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import kotlinx.coroutines.*

object LoginService {


    fun login(
        loginRequest: LoginRequest,
        successCallback: () -> Unit,
        errorCallback: () -> Unit
    ) = runBlocking {
        val response = ApiService.init().loginUser(loginRequest)
        if (response.isSuccessful) {
            successCallback()
            //save and username token
            response.body()?.data?.let {
                SessionManagerSingleton.sessionManager.logIn(it)
                SessionManagerSingleton.sessionManager.setLoggedInUsername(loginRequest.username)
            }
        } else {
            errorCallback()
        }

    }
}