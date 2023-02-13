package fr.uge.ugeoverflow.ui.components
import android.widget.Toast
import fr.uge.ugeoverflow.R
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.screens.ForgotPassword
import fr.uge.ugeoverflow.screens.LoginPage
import fr.uge.ugeoverflow.screens.SignUp
import kotlinx.coroutines.launch

@Composable
fun MainComponent(){
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppTopBar(
                onNavItemClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
                navController = navController)
        }){
        NavHost(navController = navController, startDestination = Routes.Questions.route) {

            composable(Routes.Login.route) {
                LoginPage(navController = navController)
            }

            composable(Routes.SignUp.route) {
                SignUp(navController = navController)
            }
            composable(Routes.Questions.route) {
                QuestionsHome()
            }
        }
    }
}

@Composable
fun AppTopBar(
    onNavItemClick: () -> Unit,
    navController: NavHostController
){
    val context = LocalContext.current.applicationContext
    TopAppBar(
        title = {
            Icon(painter = painterResource(id = R.drawable.ugeoverflowlogo), contentDescription = null)
        },
        actions = {

            // search icon
            IconButton(onClick = {
                Toast.makeText(context,"Search", Toast.LENGTH_LONG).show()
            }) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
            }

            // log in
            Button(onClick = {navController.navigate(Routes.Login.route) }) {
                Text(text = "Log in")
            }

            // Sign up
            Button(onClick = { navController.navigate(Routes.SignUp.route) }) {
                Text(text = "Sign up")
            }
        },
        navigationIcon = {
            Button(onClick = { onNavItemClick() }, modifier = Modifier.width(IntrinsicSize.Min)) {
            Icon(Icons.Default.Menu, "Home")
        }},
        backgroundColor = Blue,
        contentColor = White,
        elevation = 10.dp

    )
}
