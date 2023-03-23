package fr.uge.ugeoverflow.ui.screens.question

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import fr.uge.ugeoverflow.api.QuestionRequest
import fr.uge.ugeoverflow.model.MyLocation
import fr.uge.ugeoverflow.services.*
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.components.ComponentSize
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyButton
import fr.uge.ugeoverflow.ui.components.MyCard
import fr.uge.ugeoverflow.ui.routes.Routes
import fr.uge.ugeoverflow.utils.SearchableMultiSelect
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AskQuestionScreen(navController: NavHostController) {

    //Location Permissions

    val permissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    val showLocationDialog = remember { mutableStateOf(!permissions.allPermissionsGranted) }

    val myCurrentLocation = remember { mutableStateOf<MyLocation?>(null) }

    if (showLocationDialog.value) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = {
                showLocationDialog.value = false
            },
            content = {
                MyCard(
                    cardType = ComponentTypes.WarningOutline,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    header = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Location permission", style = MaterialTheme.typography.h3)
                        }
                    },
                    body = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("We like to improve our service by knowing where you are, please allow us to use your location.")
                            Text("Giving us your location will allow us to show you questions that are close to you.")
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("We will never share your location with anyone.")
                        }
                    },
                    footer = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            MyButton(
                                componentType = ComponentTypes.Success,
                                componentSize = ComponentSize.Small,
                                onClick = {
                                    permissions.launchMultiplePermissionRequest()
                                    showLocationDialog.value = false
                                }
                            ) {
                                Text("Accept")
                            }
                            MyButton(
                                componentType = ComponentTypes.Danger,
                                componentSize = ComponentSize.Small,
                                onClick = {
                                    showLocationDialog.value = false
                                    myCurrentLocation.value = MyLocation(0.0, 0.0)
                                }
                            ) {
                                Text("Refuse")
                            }
                        }
                    }
                )
            }
        )
    }


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
                                    myCurrentLocation.value = getMyLocation(context)
                                    val question = QuestionRequest(
                                        title.value,
                                        body.value,
                                        tags,
                                        location = myCurrentLocation.value
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




