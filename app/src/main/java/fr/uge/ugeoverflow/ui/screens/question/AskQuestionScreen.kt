package fr.uge.ugeoverflow.ui.screens.question

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.api.QuestionRequest
import fr.uge.ugeoverflow.services.QuestionService
import fr.uge.ugeoverflow.services.TagService
import fr.uge.ugeoverflow.services.UserBoxCardPopUp
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyButton
import fr.uge.ugeoverflow.ui.routes.Routes
import fr.uge.ugeoverflow.utils.SearchableMultiSelect
import kotlinx.coroutines.launch


@Composable
fun AskQuestionScreen(navController: NavHostController) {
    ApiService.init()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val title = remember { mutableStateOf("") }
    val body = remember { mutableStateOf("") }
    var tags = TagService.getTags()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Ask a Question") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column {
            UserBoxCardPopUp()
//            RemoteImage(
//                url = "http://localhost:8080/images/VKpWkIQ.png",
//                modifier = Modifier
//                    .padding(16.dp)
//                    .size(200.dp)
//            )


            Column(modifier = Modifier.padding(16.dp)) {
                // Title field
                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                // Content field
                OutlinedTextField(
                    value = body.value,
                    onValueChange = { body.value = it },
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth()
                )

                SearchableMultiSelect(
                    options = tags,
                    onSelectionChanged = { tags = it }
                )
                // Post button
                MyButton(
                    text = "Post",
                    onClick = {

                        val token = SessionManagerSingleton.sessionManager.getToken()
                        scope.launch {
                            if (token != null) {
                                try {
//                                    val myLocation: MyLocation? = LocationService.getLocation(context)
                                    val myLocation = null
                                    val question = QuestionRequest(
                                        title.value,
                                        body.value,
                                        tags,
                                        myLocation = myLocation
                                    )
                                    Log.e("Sending ", question.title)
                                    question.let { it1 ->
                                        QuestionService.postQuestion(
                                            it1,
                                            {
                                                Toast.makeText(
                                                    context,
                                                    "Question posted successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                navController.popBackStack()
//                                                scaffoldState.snackbarHostState.showSnackbar("Success")
                                            },
                                            {
//                                                scaffoldState.snackbarHostState.showSnackbar("Failed to post question")
                                                Toast.makeText(
                                                    context,
                                                    "Failed to post question",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    }
//                                    if (response.isSuccessful) {
//                                        navController.popBackStack()
//                                        scaffoldState.snackbarHostState.showSnackbar("Success")
//                                    } else {
//                                        scaffoldState.snackbarHostState.showSnackbar("Failed to post question")
//                                    }
                                } catch (e: Exception) {
                                    scaffoldState.snackbarHostState.showSnackbar("Failed to post question")
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "User not authenticated",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                Log.e("AskQuestionScreen", "User not authenticated")
                                scaffoldState.snackbarHostState.showSnackbar("User not authenticated")
                                navController.navigate(Routes.Login.route)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    componentType = ComponentTypes.Primary
                )

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                }


            }

        }

    }
}




