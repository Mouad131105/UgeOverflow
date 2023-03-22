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
    object Question : Routes("Qestion")
    object AskQuestion : Routes("AskQuestion")
    object OneQuestion : Routes("OneQuestion")
}