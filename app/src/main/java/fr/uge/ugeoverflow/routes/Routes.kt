package fr.uge.ugeoverflow.routes

sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object SignUp : Routes("SignUp")
    object ForgotPassword : Routes("ForgotPassword")
    object Tags : Routes("Tags")
    object Questions : Routes("Questions")
    object Users : Routes("Users")
}