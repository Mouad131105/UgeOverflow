package fr.uge.ugeoverflow.api

import android.content.Context
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException

class ApiManager(private val context: Context) {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .build()

    fun makeRequest(
        method: String,
        endpoint: String,
        body: RequestBody? = null,
        successCallback: (responseBody: String) -> Unit,
        errorCallback: (exception: Exception) -> Unit
    ) {
        val request = Request.Builder()
            .url(endpoint)
            .header("Authorization", "Bearer ${getAccessToken()}")
            .method(method, body)
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                errorCallback(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    successCallback(response.body?.string() ?: "")
                } else {
                    errorCallback(Exception("HTTP ${response.code} ${response.message}"))
                }
            }
        })
    }

    private fun getAccessToken(): String {
        return ""
    }

}