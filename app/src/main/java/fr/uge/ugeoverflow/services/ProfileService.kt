package fr.uge.ugeoverflow.services

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fr.uge.ugeoverflow.api.ImageResponse
import fr.uge.ugeoverflow.api.ReputationRequest
import fr.uge.ugeoverflow.api.UpdateProfileDTO
import fr.uge.ugeoverflow.api.UserProfileDTO
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

object ProfileService {

    val currentUsername: String?
        get() = SessionManagerSingleton.sessionManager.currentUsername.value

    fun getProfile(
        username: String?,
        onSuccess: (userProfile: UserProfileDTO) -> Unit,
        onError: () -> Unit,
    ) = runBlocking {

        val response: Response<UserProfileDTO> =
            if (username == null)
                ApiService.init().getProfile()
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
        userProfile: UpdateProfileDTO,
        onSuccess: (UserProfileDTO) -> Unit,
        onError: () -> Unit,
    ) = runBlocking {
        val token = SessionManagerSingleton.sessionManager.getToken()
        val response = ApiService.init().updateProfile(
            "Bearer $token",
            userProfile
        )
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
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
            ReputationRequest(username, note)
        )
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            onError()
        }
    }

    fun uploadImage(
        name:String,
        imageBit: Bitmap? = null,
        onSuccess: (String) -> Unit,
        onError: () -> Unit,
    ) = runBlocking {

        if (imageBit != null) {

            //length of the image in bytes should be less than 1MB or else the server will return a 413 error so we compress it
            val stream = ByteArrayOutputStream()
            imageBit.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            val byteArray = stream.toByteArray()

            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray)
            val image = MultipartBody.Part.createFormData("file", name, requestBody)
            val response = ApiService.init().uploadImage(image)
            Log.d("IMMMMMM", "uploadImage: $response")
            if (response.isSuccessful) {
                onSuccess(response.body()!!.toString())

            } else {
                onError()
            }

        } else {
            onError()
        }
    }
}