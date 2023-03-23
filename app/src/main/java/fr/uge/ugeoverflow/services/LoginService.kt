package fr.uge.ugeoverflow.services

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.api.LoginRequest
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManager
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

            SessionManagerSingleton.sessionManager.logIn(
                response.body()!!.token,
                response.body()!!.user
            )

            //Save the user image in local storage
            ImageService.saveImageToLocal(
                response.body()!!.user.username,
                response.body()!!.user.profilePicture,
                SessionManagerSingleton.sessionManager.context
            )

        } else {
            errorCallback()
        }
    }
}