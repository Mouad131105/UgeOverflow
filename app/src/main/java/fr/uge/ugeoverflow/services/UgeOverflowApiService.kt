package fr.uge.ugeoverflow.services

import fr.uge.ugeoverflow.api.LoginRequest
import fr.uge.ugeoverflow.api.LoginResponse
import fr.uge.ugeoverflow.api.QuestionResponse
import fr.uge.ugeoverflow.api.RegisterRequest
import fr.uge.ugeoverflow.model.Question
import fr.uge.ugeoverflow.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UgeOverflowApiService {
    @POST("auth/api/v1/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<Unit>

    @POST("/auth/api/v1/signin")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("api/v1/questions")
    suspend fun getAllQuestions(): Response<List<QuestionResponse>>

}