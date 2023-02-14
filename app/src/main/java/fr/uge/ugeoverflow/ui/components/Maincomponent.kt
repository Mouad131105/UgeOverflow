package fr.uge.ugeoverflow.ui.components
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
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
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.screens.ForgotPassword
import fr.uge.ugeoverflow.screens.LoginPage
import fr.uge.ugeoverflow.screens.SignUp
import fr.uge.ugeoverflow.ui.theme.Blue200
import fr.uge.ugeoverflow.ui.theme.Gray200
import fr.uge.ugeoverflow.ui.theme.White200
import fr.uge.ugeoverflow.ui.theme.poppins_bold
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

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
        },drawerContent = {
            drawerContent(items = listOf("Questions","Login","Users"), onItemClick ={ item ->
                navController.navigate(item)
            }, navController = navController)
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
            composable(Routes.ForgotPassword.route) {
                ForgotPassword(navController)
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
            Box(modifier = Modifier.fillMaxWidth(1f).fillMaxHeight()) {
            Icon(tint = Gray, painter = painterResource(id = R.drawable.ugeoverflowlogo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize())}},
        actions = {
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth(0.6f)){
                    // search icon
                    IconButton(onClick = {
                        Toast.makeText(context,"Search", Toast.LENGTH_LONG).show()
                    }, modifier = Modifier.fillMaxWidth(0.2f)) {
                        Icon(imageVector = Icons.Outlined.Search,
                            contentDescription = "Search", tint = Gray)
                    }
                    // log in
                    Button(onClick = {navController.navigate(Routes.Login.route)},
                        colors = ButtonDefaults.buttonColors(backgroundColor = White200),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.fillMaxWidth(0.48f)) {
                        Text(text = "Log in", textAlign = TextAlign.Center, style = MaterialTheme.typography.button.copy(fontSize = 10.sp, color = Blue200))
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    // Sign up
                    Button(onClick = { navController.navigate(Routes.SignUp.route)},
                        colors = ButtonDefaults.buttonColors(backgroundColor = Blue200),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.fillMaxWidth(0.75f)
                    ) {
                        Text(text = "Sign up", textAlign = TextAlign.Center, style = MaterialTheme.typography.button.copy(fontSize = 10.sp))
                    }
                }
        },
        navigationIcon = {
            TextButton(onClick = { onNavItemClick() }, modifier = Modifier.background(Transparent)) {
            Icon(Icons.Default.Menu, "Home", tint = Gray)
        }
        },
        backgroundColor = Gray200,
        contentColor = White,
        elevation = 10.dp)
}

@Composable
fun drawerContent(
    items : List<String>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    navController: NavHostController
){
    ClickableText(text = AnnotatedString("Home"),
        onClick = { navController.navigate(Routes.Login.route)},
        style = TextStyle(
            fontSize = 20.sp,
            fontFamily = poppins_bold
        ), modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 20.dp)
    )
    Text(text = "Public", fontFamily = poppins_bold, fontSize = 20.sp)
    LazyColumn(modifier){
        items(items) {
                item ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable { onItemClick(item) }) {
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)) {
                    Text(text = item)
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}


