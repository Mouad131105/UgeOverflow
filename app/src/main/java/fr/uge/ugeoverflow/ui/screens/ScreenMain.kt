package fr.uge.ugeoverflow.ui.screens
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import fr.uge.ugeoverflow.ui.routes.Routes
//import fr.uge.ugeoverflow.ui.screens.question.QuestionForm
//
//@Preview
//@Composable
//fun ScreenMain(){
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = Routes.Login.route) {
//
//        composable(Routes.Login.route) {
//            LoginPage(navController = navController, sessionManager = sessionManager)
//        }
//
//        composable(Routes.SignUp.route) {
//            SignUp(navController = navController)
//        }
//        composable(Routes.Questions.route){
//            QuestionForm(sessionManager = sessionManager, navController = navController)
//        }
//
//        composable(Routes.ForgotPassword.route) { navBackStack ->
//            ForgotPassword(navController = navController)
//        }
//    }
//}