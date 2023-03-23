package fr.uge.ugeoverflow.services

import fr.uge.ugeoverflow.api.*
import fr.uge.ugeoverflow.model.Question
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface RestController {
    @POST("auth/api/v1/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<Unit>

    @POST("/auth/api/v1/signin")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("api/v1/questions")
    suspend fun getAllQuestions(): Response<List<QuestionResponse>>@GET("api/v1/questions")


    suspend fun getAllQuestionsDto(): Response<List<OneQuestionResponse>>

    @POST("/auth/api/v1/questions")
    suspend fun postQuestion(@Header("Authorization") token: String, @Body question: QuestionRequest): Response<Question>

    @GET("/api/v1/tags")
    suspend fun getTags():Response<List<String>>

    @GET("/auth/api/v1/questions/{questionId}")
    suspend fun getQuestion(@Path("questionId") questionId: String): Response<OneQuestionResponse>

    @GET("/auth/api/v1/users/followers/{username}")
    suspend fun getFollowedUsers(@Path("username") username: String): Response<List<UserBoxResponse>>

    @GET("api/v1/questions/users/{userId}")
    suspend fun getQuestionsByUserId(@Path("userId") userId: UUID): Response<List<OneQuestionResponse>>

    @GET("auth/api/v1/users/{username}")
    suspend fun getUserInfo(@Path("username") username: String): Response<UserBoxResponse>





}