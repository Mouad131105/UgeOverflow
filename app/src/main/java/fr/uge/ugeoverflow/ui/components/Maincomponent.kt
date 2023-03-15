package fr.uge.ugeoverflow.ui.components

import QuestionScreen
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import fr.uge.ugeoverflow.R
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.uge.ugeoverflow.ui.routes.Routes
import fr.uge.ugeoverflow.ui.screens.ForgotPassword
import fr.uge.ugeoverflow.ui.screens.LoginPage
import fr.uge.ugeoverflow.ui.screens.SignUp
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.screens.question.AskQuestion
import fr.uge.ugeoverflow.ui.screens.question.QuestionsHome
import fr.uge.ugeoverflow.ui.theme.Blue200
import fr.uge.ugeoverflow.ui.theme.Gray200
import fr.uge.ugeoverflow.ui.theme.White200
import fr.uge.ugeoverflow.ui.theme.poppins_bold
import kotlinx.coroutines.launch

@Composable
fun MainComponent() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (navController.currentDestination?.route !in listOf(Routes.Questions.route, Routes.Tags.route)) {
                AppTopBar(
                    onNavItemClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                    navController = navController
                )
            }
        },
        drawerContent = {
            drawerContent(items = listOf("Questions", "Tags", "Users"), onItemClick = { item ->
                navController.navigate(item)
            }, navController = navController)
        }) {
        NavHost(navController = navController, startDestination = Routes.Questions.route) {

            composable(Routes.Login.route) {
                LoginPage(navController = navController)
            }
            composable(Routes.SignUp.route) {
                SignUp(navController = navController)
            }
            composable(Routes.Questions.route) {
                QuestionsHome(navController = navController)
            }
            composable(Routes.ForgotPassword.route) {
                ForgotPassword(navController)
            }
            composable(Routes.AskQuestion.route) {
                AskQuestion(navController)
            }
            composable(Routes.Tags.route) {
                Text(text = "Tags")
            }
            composable(Routes.OneQuestion.route) {
                QuestionScreen(navController)
            }
        }

    }
}

@Composable
fun AppTopBar(
    onNavItemClick: () -> Unit,
    navController: NavHostController,
) {
    val context = LocalContext.current.applicationContext
    val sessionManager = SessionManagerSingleton.sessionManager
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight()
            ) {
                Icon(
                    tint = Gray, painter = painterResource(id = R.drawable.ugeoverflowlogo),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        actions = {
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth(0.6f)) {
                // search icon
                IconButton(onClick = {
                    Toast.makeText(context, "Search", Toast.LENGTH_LONG).show()
                }, modifier = Modifier.fillMaxWidth(0.2f)) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search", tint = Gray
                    )
                }
                if (sessionManager.isUserLoggedIn.value) {
                    Log.d("hey", sessionManager.getToken().toString())

//                    MyButton(
//                        text = "Log out",
//                        onClick = { sessionManager.logOut() },
//                        modifier = Modifier.fillMaxWidth(0.8f),
//                        componentType = ComponentTypes.DangerOutline,
//                        componentSize = ComponentSize.Small
//                    )

                    Button(
                        onClick = { sessionManager.logOut() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = White200),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.fillMaxWidth(0.48f)
                    ) {
                        Text(
                            text = "Log out",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.button.copy(
                                fontSize = 10.sp,
                                color = Blue200
                            )
                        )
                    }
                    //UserAvatar();
                } else {
                    Log.d("hey", sessionManager.getToken().toString())
                    //Log in
//                    MyButton(
//                        content = {
//                            Text(
//                                text = "Log in",
//                                textAlign = TextAlign.Center,
//                                style = MaterialTheme.typography.button.copy(
//                                    fontSize = 10.sp,
//                                    color = Blue200
//                                )
//                            )
//                        },
//                        onClick = { navController.navigate(Routes.Login.route) },
////                        modifier = Modifier.fillMaxWidth(0.48f),
//                        componentType = ComponentTypes.Secondary,
//                        componentSize = ComponentSize.Small
//                    )
                    Button(
                        onClick = { navController.navigate(Routes.Login.route) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = White200),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.fillMaxWidth(0.48f)
                    ) {
                        Text(
                            text = "Log in",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.button.copy(
                                fontSize = 10.sp,
                                color = Blue200
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    // Sign up

//                    MyButton(
//                        content = {
//                            Text(
//                                text = "Sign up",
//                                textAlign = TextAlign.Center,
//                                style = MaterialTheme.typography.button.copy(fontSize = 10.sp)
//                            )
//                        },
//                        onClick = { navController.navigate(Routes.SignUp.route) },
////                        modifier = Modifier.fillMaxWidth(0.48f),
//                        componentType = ComponentTypes.Primary,
//                        componentSize = ComponentSize.Small
//                    )
                    Button(
                        onClick = { navController.navigate(Routes.SignUp.route) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Blue200),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.fillMaxWidth(0.75f)
                    ) {
                        Text(
                            text = "Sign up",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.button.copy(fontSize = 10.sp)
                        )
                    }
                }

            }
        },
        navigationIcon = {
            TextButton(
                onClick = { onNavItemClick() },
                modifier = Modifier.background(Transparent)
            ) {
                Icon(Icons.Default.Menu, "Home", tint = Gray)
            }
        },
        backgroundColor = Gray200,
        contentColor = White,
        elevation = 10.dp
    )
}

@Composable
fun drawerContent(
    items: List<String>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    navController: NavHostController
) {
    ClickableText(
        text = AnnotatedString("Home"),
        onClick = { navController.navigate(Routes.Login.route) },
        style = TextStyle(
            fontSize = 20.sp,
            fontFamily = poppins_bold
        ), modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 20.dp)
    )
    Text(text = "Public", fontFamily = poppins_bold, fontSize = 20.sp)
    LazyColumn(modifier) {
        items(items) { item ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable { onItemClick(item) }) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Text(text = item)
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

//@Composable
//fun UserAvatar() {
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        Text(
//            text = UserSession.getUsername() ?: "",
//            style = MaterialTheme.typography.body2,
//            modifier = Modifier.padding(start = 8.dp)
//        )
//        // Use Coil or Glide to load the user's profile picture from the imageUrl
//        Image(
//            painter = painterResource(id = R.drawable.user2),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .padding(4.dp)
//                .size(28.dp)
//                .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
//        )
//
//    }
//}

