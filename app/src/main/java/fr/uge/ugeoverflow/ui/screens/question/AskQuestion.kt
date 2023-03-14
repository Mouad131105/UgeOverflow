package fr.uge.ugeoverflow.ui.screens.question

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.api.QuestionRequest
import fr.uge.ugeoverflow.model.Location
import fr.uge.ugeoverflow.services.LocationService
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyButton
import fr.uge.ugeoverflow.ui.routes.Routes
import fr.uge.ugeoverflow.utils.SearchableMultiSelect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Composable
fun AskQuestion(navController: NavHostController) {
    val ugeOverflowApiSerivce = ApiService.init()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val title = remember { mutableStateOf("") }
    val body = remember { mutableStateOf("") }
    var tags = getTagsFromServer()

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
                        val location:Location? = LocationService.getLocation(context)
                        val question = location?.let { it1 -> QuestionRequest(title.value, body.value, tags, location= it1) }
                        val token = SessionManagerSingleton.sessionManager.getToken()
                        if (token != null) {
                            scope.launch {
                                try {
                                    Log.e("Send", question.toString())
                                    val response =
                                        question?.let { it1 ->
                                            ugeOverflowApiSerivce.postQuestion(
                                                "Bearer $token",
                                                it1
                                            )
                                        }
                                    if (response != null) {
                                        if (response.isSuccessful) {
                                            navController.popBackStack()
                                            scaffoldState.snackbarHostState.showSnackbar("Success")
                                        } else {
                                            scaffoldState.snackbarHostState.showSnackbar("Failed to post question")
                                        }
                                    }
                                } catch (e: Exception) {
                                    scaffoldState.snackbarHostState.showSnackbar("Failed to post question")
                                }
                            }
                        } else {
                            Toast.makeText(context, "User not authenticated", Toast.LENGTH_SHORT).show()
                            //scaffoldState.snackbarHostState.showSnackbar("User not authenticated")
                            navController.navigate(Routes.Login.route)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    componentType = ComponentTypes.Primary
                )


            }

        }

    }
}

fun getTagsFromServer(): List<String> = runBlocking {
    val re = ApiService.init().getTags()
    val tags: List<String> = if (re.isSuccessful) {
        re.body() ?: listOf()
    } else listOf()
    Log.e("TAGS", tags.toString())
    tags

}
