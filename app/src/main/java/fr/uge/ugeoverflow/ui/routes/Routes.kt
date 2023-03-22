package fr.uge.ugeoverflow.ui.routes

sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object SignUp : Routes("SignUp")
    object ForgotPassword : Routes("ForgotPassword")
    object Tags : Routes("Tags")
    object Questions : Routes("Questions")
    object Users : Routes("Users")
    object UserDetails : Routes("UserDetails/{userId}")
    object Question : Routes("Question")
    object AskQuestion : Routes("AskQuestion")
    object OneQuestion : Routes("questions/{questionId}")
}