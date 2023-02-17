package fr.uge.ugeoverflow.api

import fr.uge.ugeoverflow.model.Question
import fr.uge.ugeoverflow.model.User
import fr.uge.ugeoverflow.services.UgeOverflowApiService
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object UgeOverflowApi {
    private const val BASE_URL = "http://192.168.1.13:8080" //Ici il faut mettre l'address ipv4 local

    fun create(): UgeOverflowApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(UgeOverflowApiService::class.java)
    }

    fun createWithAuth(userSession: UserSession): UgeOverflowApiService {
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val originalRequest = chain.request()
                val token = userSession.getToken()
                if (token == null) {
                    chain.proceed(originalRequest)
                } else {
                    val requestWithAuth = originalRequest.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                    chain.proceed(requestWithAuth)
                }
            }
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient) //  adding client
            .build()
        return retrofit.create(UgeOverflowApiService::class.java)
    }


}
