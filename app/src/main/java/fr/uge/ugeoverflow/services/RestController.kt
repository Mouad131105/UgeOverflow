package fr.uge.ugeoverflow.services

import android.location.Location
import android.location.LocationRequest
import fr.uge.ugeoverflow.api.*
import fr.uge.ugeoverflow.model.Question
import retrofit2.Response
import retrofit2.http.*

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

    @POST("/api/v1/location")
    suspend fun saveLocation(@Header("Authorization") token: String, @Body location: Location): Response<Unit>

    @GET("/api/v1/location/{userId}")
    suspend fun getLocation(@Header("Authorization") token: String, @Path("userId") userId: String): Response<Location>

    @PUT("/api/v1/location")
    suspend fun updateLocation(@Header("Authorization") token: String, @Body location: Location): Response<Unit>

}