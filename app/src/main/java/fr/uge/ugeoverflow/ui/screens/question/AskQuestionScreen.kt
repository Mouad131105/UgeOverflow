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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.QuestionRequest
import fr.uge.ugeoverflow.model.MyLocation
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.services.*
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.components.ComponentSize
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyButton
import fr.uge.ugeoverflow.ui.components.MyCard
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
                            Text(stringResource(id = R.string.user_location))
                            Text(stringResource(id = R.string.questions_close_toyou))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(stringResource(id = R.string.dont_share_location))
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
                                Text(stringResource(id = R.string.accept))
                            }
                            MyButton(
                                componentType = ComponentTypes.Danger,
                                componentSize = ComponentSize.Small,
                                onClick = {
                                    showLocationDialog.value = false
                                    myCurrentLocation.value = MyLocation(0.0, 0.0)
                                }
                            ) {
                                Text(stringResource(id = R.string.refuse))
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
                title = { Text(stringResource(id = R.string.ask_a_question)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
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
                    label = { Text(stringResource(id = R.string.title)) },
                    modifier = Modifier.fillMaxWidth()
                )
                // Content field
                OutlinedTextField(
                    value = body.value,
                    onValueChange = { body.value = it },
                    label = { Text(stringResource(id = R.string.content)) },
                    modifier = Modifier.fillMaxWidth()
                )

                SearchableMultiSelect(
                    options = tags,
                    onSelectionChanged = { tags = it }
                )
                // Post button
                MyButton(
                    text = stringResource(id = R.string.post),
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
                                                    context.getString(R.string.question_posted_successfully),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                navController.popBackStack()
                                            },
                                            {
                                                Toast.makeText(
                                                    context,
                                                    context.getString(R.string.failed_to_post_question),

                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    }

//                                    }
                                } catch (e: Exception) {
                                    scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.failed_to_post_question))
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.user_not_authenticated),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                Log.e("AskQuestionScreen", "User not authenticated")
                                scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.user_not_authenticated))
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




