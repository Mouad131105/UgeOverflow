package fr.uge.ugeoverflow.services

import android.location.Location
import android.location.LocationRequest
import fr.uge.ugeoverflow.api.*
import fr.uge.ugeoverflow.model.Question
import okhttp3.ResponseBody
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
    suspend fun postQuestion(
        @Header("Authorization") token: String,
        @Body question: QuestionRequest
    ): Response<Question>

    @POST("/auth/api/v1/answers")
    suspend fun postAnswer(@Header("Authorization") token: String, questionId: String,  @Body answer: AnswerRequest): Response<AnswerResponse>

    @POST("/auth/api/v1/comments/{overflowId}")
    suspend fun postComment(@Header("Authorization") token: String,@Path("overflowId") overflowId: String, @Body comment: CommentRequest): Response<CommentResponse>

    @GET("/api/v1/tags")
    suspend fun getTags(): Response<List<String>>

    @GET("/images/{name}")
    suspend fun getImage(@Path("name") name: String): Response<ResponseBody>

    @GET("/auth/api/v1/users/profile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UserProfileDTO>

    @GET("/auth/api/v1/users/{username}")
    suspend fun getUserProfile(@Path("username") username: String): Response<UserProfileDTO>

    @PUT("/auth/api/v1/users/profile")
    suspend fun updateProfile(@Header("Authorization") token: String, userProfile: UserProfileDTO): Response<UserProfileDTO>

    @GET("/auth/api/v1/users/{username}/follow")
    suspend fun followUser(@Header("Authorization") token: String,@Path("username") username: String): Response<UserProfileDTO>

    @GET("/auth/api/v1/users/{username}/unfollow")
    suspend fun unfollowUser(@Header("Authorization") token: String,@Path("username") username: String): Response<UserProfileDTO>

    @POST("/auth/api/v1/users/{username}/reputation")
    suspend fun addReputation(@Header("Authorization") token: String,@Path("username") username: String, note:Int): Response<UserProfileDTO>

    @GET("/auth/api/v1/questions/{questionId}")
    suspend fun getQuestion(@Path("questionId") questionId: String): Response<OneQuestionResponse>


}