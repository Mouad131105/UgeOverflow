package fr.uge.ugeoverflow.routes

sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object SignUp : Routes("SignUp")
    object ForgotPassword : Routes("ForgotPassword")
    object Tags : Routes("Tags")
    object TagDetails : Routes("TagDetails")
    object Questions : Routes("Questions")
    object Users : Routes("Users")
    object UserDetails : Routes("UserDetails/{userId}")

    object AskQuestion : Routes("AskQuestion")
    object Profile : Routes("Profile")
    object Question : Routes("Question")
    object OneQuestion : Routes("questions/{questionId}")
    object SearchResults : Routes("SearchResults/{keyword}")

}