package fr.uge.ugeoverflow.session

import fr.uge.ugeoverflow.services.RestController
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private val BASE_URL =
//        NetworkService.getBaseURL()
        "http://172.20.10.7:8080" //Ici il faut mettre l'address ipv4 local

    fun init(): RestController {
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val originalRequest = chain.request()
                val token = SessionManagerSingleton.sessionManager.getToken()
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

        return retrofit.create(RestController::class.java)
    }
}
