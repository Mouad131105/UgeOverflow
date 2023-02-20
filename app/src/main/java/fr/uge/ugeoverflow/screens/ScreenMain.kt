package fr.uge.ugeoverflow.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.uge.ugeoverflow.SessionManager.ApiManager
import fr.uge.ugeoverflow.SessionManager.SessionManager
import fr.uge.ugeoverflow.routes.Routes

@Composable
fun ScreenMain(apiManager :ApiManager, sessionManager : SessionManager) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Login.route) {

        composable(Routes.Login.route) {
            LoginPage(navController = navController, apiManager = apiManager, sessionManager = sessionManager)
        }

        composable(Routes.SignUp.route) {
            SignUp(navController = navController)
        }

        composable(Routes.ForgotPassword.route) { navBackStack ->
            ForgotPassword(navController = navController)
        }
    }
}