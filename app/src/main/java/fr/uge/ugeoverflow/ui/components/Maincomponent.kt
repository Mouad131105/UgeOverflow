package fr.uge.ugeoverflow.ui.components

import QuestionScreen
import TagsScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import fr.uge.ugeoverflow.R
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.uge.ugeoverflow.data.UserDataProvider
import fr.uge.ugeoverflow.model.Tag
import fr.uge.ugeoverflow.ui.routes.Routes
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.screens.ForgotPasswordScreen
import fr.uge.ugeoverflow.ui.screens.LoginScreen
import fr.uge.ugeoverflow.ui.screens.SignUpScreen
import fr.uge.ugeoverflow.ui.screens.profile.UserProfileScreen
import fr.uge.ugeoverflow.ui.screens.question.AskQuestionScreen
import fr.uge.ugeoverflow.ui.screens.question.QuestionsHomeScreen
import fr.uge.ugeoverflow.ui.theme.Blue200
import fr.uge.ugeoverflow.ui.theme.Gray200
import fr.uge.ugeoverflow.ui.theme.White200
import fr.uge.ugeoverflow.ui.theme.poppins_bold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

val users = UserDataProvider.generateUsers()

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
                items = listOf("Questions", "Tags", "Users", "Profile"),
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
            composable(Routes.Questions.route) {
                QuestionsHomeScreen(navController = navController)
            }
            composable(Routes.ForgotPassword.route) {
                ForgotPasswordScreen(navController)
            }
            composable(Routes.AskQuestion.route) {
                AskQuestionScreen(navController)
            }
            composable(Routes.Tags.route) {
                Text(text = "Tags")
            }
            composable(Routes.Users.route) {
                UserListScreen(users, navController)
            }

            composable(Routes.Profile.route) {
                UserProfileScreen(navController = navController)
            }
            composable("${Routes.Profile.route}/{username}") { backStackEntry ->
                val username: String = backStackEntry.arguments?.getString("username") ?: throw Exception("Username is null")
                UserProfileScreen(navController, username)
            }
            composable(Routes.Tags.route) {

                val tags = listOf(
                    Tag(
                        1,
                        "Programming",
                        "Questions related to programming and software development hfdjshfkldshkfjsdmfjsmfjdsmljfmldsjfsdlmqjfmdsqjfmdlsjflsdmjfqlsdmjfsdlfefEZFEZFezfZEFDzefgergtrefezfrgtrgtrtrtrgsDNFJKDSHFJKSDHFJSDHFJKSDHFJKHSDJKFHDSJKHFkjdshfjkHDFJKHsqdjkdfhsjkqfhsdjkfhkjsdhfjsdkhfkjsdhfjk"
                    ),
                    Tag(
                        2,
                        "Java",
                        "Questions related to the Java programming language sdvqhdskvjdskmvjsdkljvmsdlqjvmlsqdjvlmsdqjflmsdjflmjsdlmqfjlmsdqjflmsdjqflmsdjqmlfjqsdmlfjmsdlqfjrgretrtgtretrgthet'rgtht'rgerthetrgtrethre DSFSDJKhfjksdHFJKSDHFJKHSDKJFHDSJKHFKJdshfjkSDHFKJsdhfjksdhfjkSDHF"
                    ),
                    Tag(
                        3,
                        "Kotlin",
                        "Questions related to the Kotlin programming languagesdqvsdnflksdjklsdfkjsdmklfjlsdmjflmsdjflmsj kldfshqvklsfhvkhsfdklhsdlkhfqskldfhlksdqhflksdqhfregrergretggretgtnhgthgndfthhnhhtrhtrhnhrth dsfhdjfkhdsjkfhjkdshfkjHFJKHDfkjdhsjkfhKJSDHFJKSDHFKJSDHF"
                    ),
                    Tag(
                        4,
                        "Android",
                        "Questions related to the Android mobile operating system dskvbqsdjkbvkjsdbvjkdsbhvjsdqhfklsdhqfklsdhqlfkhsdqklfhsdqkfhsdqlfhsdklqfhlksdqhfsdklqftthgtrgtreghtehtrndthehtrhendrtheztrtherhtregh jsdfhqjdskhfjdkhfjkdqshfjkshqfjkhdskjqfhdjskhfjkdsqhfjksdhfkjqsdhfkjsdhqfjksdhqkf"
                    ),
                    Tag(
                        5,
                        "iOS",
                        "Questions related to the iOS mobile operating system jsdfbvklsdnhklvsdhqfklhsdqklfhsdklqhfklsdqhflksdqhflksdncklsdncklsdqncklsdqhvlkdsnfdsnkcnsdklqncvgret'reghghtnthrngfnghg,nthrnh,thrnrthynthrthrrthtrhrrdsjkfhqsdjkfhdjkshfqkjdshfkjqsdhjkfhqsdkjfhdskjfhqsjkf"
                    ),
                    Tag(
                        6,
                        "Web Development",
                        "Questions related to web development technologies and frameworks ksjdbhvfjksdhfjkhsdjkfhsdjkhfjksdqfbkjsqdfhjksqdfjksdqhfvjksdqhvbkjsdqvhsdjkqhfhtreth(erythrhthy(rthr(ythrhythhrjrh(ythry(thrthretds,f;sd,f,;sdf;:n,qsdnf,dsnf,;nd,;fnsd,;fn,qs;nfse;,d:fds"
                    ),
                    Tag(
                        7,
                        "JavaScript",
                        "Questions related to the JavaScript programming language nbsdkjvsdjkhfjksdhfjksdhqfjksdhqfjksdhqfjkhsdqjkfgsdqjkfgsdqjkfgsdjkqfdsjkqfhsdjklhflksdhfdfjkdhjfhsjdhfksdjhfjskdhfjksdhfjsdhfjkhsdjkfhjsdkfhjsdkhfjksdhfjksdhfjksdhfjksdhfkjsdhfkjsdhfksdjfhsdjkfhsdjkf"
                    ),
                    Tag(
                        8,
                        "Python",
                        "Questions related to the Python programming language sdhjkvfgsdjkqgfjksdgfkjsdgfjksdgqfjksdgjfkgsdkjfgsdjkfgjklsdgfjlksdgflksdhflksdqhfklsdqhjfklsdqfhlksdqfhfskdljfklsdjfkljsdklfjsdkljfklsdjflksdjflksjdflksdjflksdjklfjsdlkfjlksdjfklsdjflksdjflksdjf"
                    ),
                    Tag(
                        9,
                        "hamid",
                        "Questions related to the Python programming language sdhjkvfgsdjkqgfjksdgfkjsdgfjksdgqfjksdgjfkgsdkjfgsdjkfgjklsdgfjlksdgflksdhflksdqhfklsdqhjfklsdqfhlksdqfhfskdljfklsdjfkljsdklfjsdkljfklsdjflksdjflksjdflksdjflksdjklfjsdlkfjlksdjfklsdjflksdjflksdjf"
                    ),
                    Tag(
                        10,
                        "JEE",
                        "Questions related to the Python programming language sdhjkvfgsdjkqgfjksdgfkjsdgfjksdgqfjksdgjfkgsdkjfgsdjkfgjklsdgfjlksdgflksdhflksdqhfklsdqhjfklsdqfhlksdqfhfskdljfklsdjfkljsdklfjsdkljfklsdjflksdjflksjdflksdjflksdjklfjsdlkfjlksdjfklsdjflksdjflksdjf"
                    ),
                    Tag(
                        11,
                        "Spring",
                        "Questions related to the Python programming language sdhjkvfgsdjkqgfjksdgfkjsdgfjksdgqfjksdgjfkgsdkjfgsdjkfgjklsdgfjlksdgflksdhflksdqhfklsdqhjfklsdqfhlksdqfhfskdljfklsdjfkljsdklfjsdkljfklsdjflksdjflksjdflksdjflksdjklfjsdlkfjlksdjfklsdjflksdjflksdjf"
                    ),
                    Tag(
                        12,
                        "Hibernate",
                        "Questions related to the Python programming language sdhjkvfgsdjkqgfjksdgfkjsdgfjksdgqfjksdgjfkgsdkjfgsdjkfgjklsdgfjlksdgflksdhflksdqhfklsdqhjfklsdqfhlksdqfhfskdljfklsdjfkljsdklfjsdkljfklsdjflksdjflksjdflksdjflksdjklfjsdlkfjlksdjfklsdjflksdjflksdjf"
                    )


                )

                TagsScreen(tags = tags)
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
    val isSearchVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

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
                    // profile icon
                    var expanded by remember { mutableStateOf(false) }

                    Box(
                        modifier = Modifier
                            .wrapContentSize(Alignment.TopEnd)
                    )
                    {
                        IconButton(onClick = { expanded = true }) {
                            Image(
                                //TODO replace by user image url
                                painter = painterResource(id = R.drawable.user3),
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .padding(3.dp)
                                    .fillMaxWidth(0.2f)
                                    .size(30.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
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
                                Text("Log out")
                            }
                        }
                    }
//                    Button(
//                        onClick = { sessionManager.logOut() },
//                        colors = ButtonDefaults.buttonColors(backgroundColor = White200),
//                        contentPadding = PaddingValues(0.dp),
//                        modifier = Modifier.fillMaxWidth(0.48f)
//                    ) {
//                        Text(
//                            text = "log out ",
//                            textAlign = TextAlign.Center,
//                            style = MaterialTheme.typography.button.copy(
//                                fontSize = 10.sp,
//                                color = Blue200
//                            )
//                        )
//                    }
                } else {
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
    if (isSearchVisible) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
    }
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
    ClickableText(
        text = AnnotatedString("Home"),
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


