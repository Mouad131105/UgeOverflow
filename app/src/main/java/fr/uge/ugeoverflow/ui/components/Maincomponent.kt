package fr.uge.ugeoverflow.ui.components

//import fr.uge.ugeoverflow.model.Tag

import QuestionScreen
import TagScreen
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.data.QuestionManager
import fr.uge.ugeoverflow.data.UserDataProvider
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.services.ImageService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.screens.ForgotPasswordScreen
import fr.uge.ugeoverflow.ui.screens.LoginScreen
import fr.uge.ugeoverflow.ui.screens.SignUpScreen
import fr.uge.ugeoverflow.ui.screens.profile.UserProfileScreen
import fr.uge.ugeoverflow.ui.screens.question.AskQuestionScreen
import fr.uge.ugeoverflow.ui.screens.question.QuestionsHome

import fr.uge.ugeoverflow.ui.theme.Blue200
import fr.uge.ugeoverflow.ui.theme.Gray200
import fr.uge.ugeoverflow.ui.theme.White200
import fr.uge.ugeoverflow.ui.theme.poppins_bold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

val users = UserDataProvider.generateUsers()
val questionManager = QuestionManager

@Composable
fun MainComponent() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (navController.currentDestination?.route !in listOf(
                    Routes.UserDetails.route,
                    Routes.Questions.route,
                    Routes.Tags.route,
                    Routes.Profile.route,
                )
            ) {
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
            DrawerContent(
                items = listOf("Questions", "Tags", stringResource(id = R.string.users)),
                onItemClick = { item ->
                    navController.navigate(item)
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                },
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope
            )
        }) {
        NavHost(navController = navController, startDestination = Routes.Questions.route) {

            composable(Routes.Login.route) {
                LoginScreen(navController = navController)
            }
            composable(Routes.SignUp.route) {
                SignUpScreen(navController = navController)
            }

            composable(Routes.ForgotPassword.route) {
                ForgotPasswordScreen(navController)
            }
            composable(Routes.AskQuestion.route) {
                AskQuestionScreen(navController)
            }
            composable("${Routes.TagDetails.route}/{tagName}") { backStackEntry ->
                val tagName: String = backStackEntry.arguments?.getString("tagName")
                    ?: throw Exception("TagName is null")
                TagDetails(navController = navController, tagName = tagName)
            }
            composable(Routes.Tags.route) {
                Text(text = "Tags")
            }
            composable(Routes.Users.route) {
                UserListScreen(navController)
            }

            composable(Routes.Profile.route) {
                UserProfileScreen(navController = navController)
            }
            composable("${Routes.Profile.route}/{username}") { backStackEntry ->
                val username: String = backStackEntry.arguments?.getString("username")
                    ?: throw Exception("Username is null")
                UserProfileScreen(navController, username)
            }
            composable(Routes.Tags.route) {
                TagScreen(navController)
            }
            composable(Routes.Questions.route) {
                QuestionsHome(navController = navController)
            }
            composable("${Routes.OneQuestion.route}/{questionId}") { backStackEntry ->
                val questionId: String? = backStackEntry.arguments?.getString("questionId")
                val question = questionId?.let { it1 -> questionManager.getQuestionById(it1) }

                if (question != null) {
                    QuestionScreen(navController,question.id)
                }
            }
            composable("${Routes.Question.route}/{id}") { backStackEntry ->
                val id: String = backStackEntry.arguments?.getString("id")
                    ?: throw Exception("Id is null")
                QuestionScreen(navController, id)
            }
            composable(Routes.SearchResults.route) { backStackEntry ->
                val keyword: String? = backStackEntry.arguments?.getString("keyword")
                keyword?.let { SearchResultsScreen(navController, keyword) }
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
    val scope = rememberCoroutineScope()
    var showSearchField by remember { mutableStateOf(false) }
    val imageData = remember {
        mutableStateOf(
            ImageService.getImageFromServer(
                sessionManager.getImage().toString()
            )
        )
    }
    //if the token or the session manager changes the imageData is updated
    LaunchedEffect(sessionManager) {
        imageData.value = ImageService.getImageFromServer(sessionManager.getImage().toString())
    }

    var searchText by remember { mutableStateOf("") }
    if (showSearchField){
    Column() {
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        TextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
            },
            label = { Text(text = "Rechercher") },
            singleLine = true,
            modifier = Modifier
                .fillMaxHeight(0.1f)
                .fillMaxWidth(1f),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    scope.launch {
                        navController.navigate(Routes.SearchResults.route.replace("{keyword}", searchText))
                    }
                }
            )
        )
    }

   }
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
                    showSearchField = !showSearchField
                }, modifier = Modifier.fillMaxWidth(0.2f)) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = context.getString(R.string.search), tint = Gray
                    )
                }
                if (sessionManager.isUserLoggedIn.value) {
                    Log.d("hey", sessionManager.getToken().toString())
                    var expanded by remember { mutableStateOf(false) }

                    Box(
                        modifier = Modifier
                            .wrapContentSize(Alignment.TopEnd)
                    )
                    {

                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                contentDescription = "profile",
                                tint = Gray
                            )
                            imageData.value?.let {
                                Image(
                                    bitmap = it,
                                    contentDescription = "Profile",
                                    modifier = Modifier
                                        .padding(3.dp)
                                        .fillMaxWidth(0.2f)
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .border(1.dp, Gray, CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                navController.navigate(Routes.Profile.route)
                                expanded = false
                            }) {
                                Text("Profile")
                            }
                            DropdownMenuItem(onClick = {
                                expanded = false
                            }) {
                                Text("Settings")
                            }
                            Divider()
                            DropdownMenuItem(onClick = {
                                expanded = false
                                sessionManager.logOut()
                            }) {
                                Text(stringResource(id = R.string.logout))
                            }
//                            ImageScreen(context)

                        }
                    }
                } else {
                    Button(
                        onClick = { navController.navigate(Routes.Login.route) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = White200),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.fillMaxWidth(0.48f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.login),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.button.copy(
                                fontSize = 10.sp,
                                color = Blue200
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    // Sign up
                    Button(
                        onClick = { navController.navigate(Routes.SignUp.route) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Blue200),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.fillMaxWidth(0.75f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.signup),
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
                Icon(Icons.Default.Menu, context.getString(R.string.home), tint = Gray)
            }
        },
        backgroundColor = Gray200,
        contentColor = White,
        elevation = 10.dp
    )
}


@Composable
fun DrawerContent(
    items: List<String>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    LanguageSelector()

    ClickableText(
        text = AnnotatedString(stringResource(id = R.string.home)),
        onClick = {

            navController.navigate(Routes.Login.route)
            scope.launch {
                scaffoldState.drawerState.close()
            }
        },
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


@Composable
fun LanguageSelector() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf(Locale.getDefault()) }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Box(modifier = Modifier.wrapContentSize()) {
        TextButton(onClick = { expanded = true }) {
            Icon(
                tint = Gray, painter = painterResource(id = R.drawable.translate),
                contentDescription = null,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                selectedLanguage = Locale.FRENCH
                updateConfiguration(context, selectedLanguage)
                expanded = false
            }) {
                Text(text = "FR")
            }
            DropdownMenuItem(onClick = {
                selectedLanguage = Locale.ENGLISH
                updateConfiguration(context, selectedLanguage)
                expanded = false
            }) {
                Text(text = "EN")
            }
        }
    }
}

fun updateConfiguration(context: Context, locale: Locale) {
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)
    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    (context as Activity).recreate()
}



