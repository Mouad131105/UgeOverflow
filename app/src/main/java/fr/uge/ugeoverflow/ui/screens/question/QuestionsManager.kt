package fr.uge.ugeoverflow.ui.screens.question

import fr.uge.ugeoverflow.api.OneQuestionResponse
import fr.uge.ugeoverflow.api.UserBoxResponse
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashSet

object QuestionsManager {
    val apiService = ApiService.init()

    private suspend fun getFollowedUsersQuestions(
        username: String,
        level: Int,
        map: TreeMap<Int, HashSet<OneQuestionResponse>>,
        maxLevel: Int
    ) {


        if (level <= maxLevel) {
            var questions: HashSet<OneQuestionResponse>
            var l = HashSet<OneQuestionResponse>()
            val followedUsersResponse = apiService.getFollowedUsers(username)
            val followedUsers = followedUsersResponse.body() ?: emptyList()
            var followedQuestions: List<OneQuestionResponse>

            followedUsers.forEach { followedUser ->
                if (followedUser.username != username) {
                    followedQuestions =
                        apiService.getQuestionsByUserId(followedUser.id).body() ?: emptyList()
                    l.addAll(followedQuestions)
                }
            }

            if (map.containsKey(level)) {
                questions = map[level]!!
                questions.addAll(l)
                map[level] = questions
            } else {
                map[level] = l
            }

            followedUsers.forEach { followedUser ->
                if (followedUser.username != username) {
                    getFollowedUsersQuestions(followedUser.username, level + 1, map, maxLevel)
                }
            }
        }
    }

    suspend fun getQuestionsForAuthenticatedUser() : List<OneQuestionResponse> {

     var questions: HashSet<OneQuestionResponse> = HashSet()
     val map: TreeMap<Int, HashSet<OneQuestionResponse>> = TreeMap()
    val sessionManager = SessionManagerSingleton.sessionManager
     val currentUser = sessionManager.getLoggedInUsername()


     val userFollowing: List<UserBoxResponse> =
        currentUser?.let { apiService.getFollowedUsers(it).body() } ?: emptyList()

    if (userFollowing.isNotEmpty())
    {
        if (currentUser != null) {
            getFollowedUsersQuestions(currentUser, 1, map, 3)
        }
        questions.addAll(map.values.stream().flatMap { obj: Set<*> -> obj.stream() }
            .map { it as OneQuestionResponse }
            .collect(Collectors.toSet()))

    }
        return questions.stream().collect(Collectors.toList())
}
       /* questions.addAll(questionService.findAllQuestionDTO())
        val currentUserQuestions: Any = questions.stream().filter( { question: OneQuestionResponse ->
            question.getUser().getId().equals(currentUser.getId())
        }).collect(Collectors.toList<Any>())
        questions.removeAll(currentUserQuestions)
        questions.addAll(currentUserQuestions)*/
}