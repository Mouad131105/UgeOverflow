package fr.uge.ugeoverflow.services

import fr.uge.ugeoverflow.api.UserProfileDTO
import fr.uge.ugeoverflow.model.User
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import retrofit2.Response

object ProfileService {

    val currentUsername: String?
        get() = SessionManagerSingleton.sessionManager.currentUsername.value

    fun getProfile(
        username: String?,
        onSuccess: (userProfile: UserProfileDTO) -> Unit,
        onError: () -> Unit,
    ) = runBlocking {

        val response: Response<UserProfileDTO> = if (username == null)
            ApiService.init().getProfile(
                "Bearer ${SessionManagerSingleton.sessionManager.getToken()}"
            )
        else
            ApiService.init().getUserProfile(
                username
            )

        if (response.isSuccessful) {
            val userProfile = response.body()!!
            onSuccess(userProfile)
        } else {
            onError()
        }
    }

    fun updateProfile(
        userProfile: UserProfileDTO,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) = runBlocking {
        val token = SessionManagerSingleton.sessionManager.getToken()
        val response = ApiService.init().updateProfile(
            "Bearer $token",
            userProfile
        )
        if (response.isSuccessful) {
            onSuccess()
        } else {
            onError()
        }
    }

    fun followUser(
        username: String,
        onSuccess: (UserProfileDTO) -> Unit,
        onError: () -> Unit,
    ) = runBlocking {
        val token = SessionManagerSingleton.sessionManager.getToken()
        val response = ApiService.init().followUser(
            "Bearer $token",
            username
        )
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            onError()
        }
    }

    fun unfollowUser(
        username: String,
        onSuccess: (UserProfileDTO) -> Unit,
        onError: () -> Unit,
    ) = runBlocking {
        val token = SessionManagerSingleton.sessionManager.getToken()
        val response = ApiService.init().unfollowUser(
            "Bearer $token",
            username
        )
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            onError()
        }
    }

    fun addReputation(
        username: String,
        note: Int,
        onSuccess: (UserProfileDTO) -> Unit,
        onError: () -> Unit,
    ) = runBlocking {
        val token = SessionManagerSingleton.sessionManager.getToken()
        val response = ApiService.init().addReputation(
            "Bearer $token",
            username,
            note
        )
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            onError()
        }
    }
}