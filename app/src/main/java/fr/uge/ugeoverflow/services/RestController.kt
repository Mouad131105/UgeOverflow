package fr.uge.ugeoverflow.services

import fr.uge.ugeoverflow.api.*
import fr.uge.ugeoverflow.model.Question
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RestController {
    @POST("auth/api/v1/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<Unit>

    @POST("/auth/api/v1/signin")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("api/v1/questions")
    suspend fun getAllQuestions(): Response<List<QuestionResponse>>

    @POST("/auth/api/v1/questions")
    suspend fun postQuestion(@Header("Authorization") token: String, @Body question: QuestionRequest): Response<Question>

    @GET("/api/v1/tags")
    suspend fun getTags():Response<List<String>>
}