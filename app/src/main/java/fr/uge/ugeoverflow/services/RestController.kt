package fr.uge.ugeoverflow.services

import fr.uge.ugeoverflow.api.*
import fr.uge.ugeoverflow.model.Question
import fr.uge.ugeoverflow.model.VOTE_TYPE
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

    @POST("/auth/api/v1/answers/{questionId}")
    suspend fun postAnswer(
        @Header("Authorization") token: String,
        @Path("questionId") questionId: String,
        @Body answer: AnswerRequest
    ): Response<OneQuestionResponse>

    @POST("/auth/api/v1/comments/{questionId}")
    suspend fun postCommentForQuestion(
        @Header("Authorization") token: String,
        @Path("questionId") questionId: String,
        @Body comment: CommentRequest
    ): Response<OneQuestionResponse>

    @POST("/auth/api/v1/comments/{questionId}/{answerId}")
    suspend fun postCommentForAnswer(
        @Header("Authorization") token: String,
        @Path("questionId") questionId: String,
        @Path("answerId") answerId: String,
        @Body comment: CommentRequest
    ): Response<OneQuestionResponse>

    @GET("/api/v1/tags")
    suspend fun getTags(): Response<List<String>>

    @GET("/api/v1/tag")
    suspend fun getAllTags(): Response<List<TagResponse>>

    @GET("/images/{name}")
    suspend fun getImage(@Path("name") name: String): Response<ResponseBody>

    @GET("/auth/api/v1/users/profile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UserProfileDTO>

    @GET("/auth/api/v1/users/{username}")
    suspend fun getUserProfile(@Path("username") username: String): Response<UserProfileDTO>

    @PUT("/auth/api/v1/users/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        userProfile: UserProfileDTO
    ): Response<UserProfileDTO>

    @GET("/auth/api/v1/users/{username}/follow")
    suspend fun followUser(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Response<UserProfileDTO>

    @GET("/auth/api/v1/users/{username}/unfollow")
    suspend fun unfollowUser(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Response<UserProfileDTO>

    @POST("/auth/api/v1/users/reputation")
    suspend fun addReputation(
        @Header("Authorization") token: String,
        @Body reputationRequest: ReputationRequest
    ): Response<UserProfileDTO>

    @GET("/api/v1/questions/{questionId}")
    suspend fun getQuestion(@Path("questionId") questionId: String): Response<OneQuestionResponse>

    @POST("/auth/api/v1/answers/{answerId}/vote")
    suspend fun voteAnswer(
        @Header("Authorization") token: String,
        @Path("answerId") answerId: String,
        voteType: VOTE_TYPE
    ): Response<AnswerResponse>


    @GET("/api/v1/tags/{tagName}")
    suspend fun getQuestionsByTag(@Path("tagName") tagName: String): Response<List<QuestionResponse>>

    @DELETE("/auth/api/v1/questions/{questionId}")
    suspend fun deleteQuestion(@Header("Authorization") token: String): Response<Unit>

    @PUT("/auth/api/v1/questions/{questionId}")
    suspend fun updateQuestion(
        @Header("Authorization") token: String,
        @Path("questionId") questionId: String,
        @Body question: QuestionRequest
    ): Response<OneQuestionResponse>

    @DELETE("/auth/api/v1/answers/{answerId}")
    suspend fun deleteAnswer(@Header("Authorization") token: String): Response<OneQuestionResponse>

    @PUT("/auth/api/v1/answers/{answerId}")
    suspend fun updateAnswer(
        @Header("Authorization") token: String,
        @Path("answerId") answerId: String,
        @Body answer: AnswerRequest
    ): Response<AnswerResponse>

    @DELETE("/auth/api/v1/comments/{commentId}")
    suspend fun deleteComment(
        @Header("Authorization") token: String,
        @Path("commentId") commentId: String
    ): Response<Unit>

    @PUT("/auth/api/v1/comments/{commentId}")
    suspend fun updateComment(
        @Header("Authorization") token: String,
        @Path("commentId") commentId: String,
        @Body comment: CommentRequest
    ): Response<CommentResponse>


}