package fr.uge.ugeoverflow.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.RegisterRequest
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.CustomTopAppBar
import fr.uge.ugeoverflow.ui.components.MyButton
import fr.uge.ugeoverflow.ui.theme.poppins_light
import fr.uge.ugeoverflow.ui.theme.poppins_medium
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun SignUpScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBar(navController)
    }
}

@Composable
fun ScaffoldWithTopBar(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current;
    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = scaffoldState.snackbarHostState),
        topBar = {
            CustomTopAppBar(navController, context.getString(R.string.inscription), true)
        },
        content = {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val lastname = remember { mutableStateOf(TextFieldValue()) }
                val firstname = remember { mutableStateOf(TextFieldValue()) }
                val username = remember { mutableStateOf(TextFieldValue()) }
                val password = remember { mutableStateOf(TextFieldValue()) }
                val email = remember { mutableStateOf(TextFieldValue()) }
                val passwordConfirm = remember { mutableStateOf(TextFieldValue()) }

                Text(
                    text = context.getString(R.string.inscription),
                    style = TextStyle(fontSize = 40.sp, fontFamily = poppins_medium)
                )

                TextField(
                    value = lastname.value,
                    onValueChange = { lastname.value = it },
                    label = {
                        Text(
                            text = context.getString(R.string.lastname),
                            style = TextStyle(fontFamily = poppins_light)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                TextField(
                    value = firstname.value,
                    onValueChange = { firstname.value = it },
                    label = {
                        Text(
                            text = context.getString(R.string.firstname),
                            style = TextStyle(fontFamily = poppins_light)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                TextField(
                    value = username.value,
                    onValueChange = { username.value = it },
                    label = {
                        Text(
                            text = context.getString(R.string.username),
                            style = TextStyle(fontFamily = poppins_light)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                TextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = {
                        Text(
                            text = context.getString(R.string.email),
                            style = TextStyle(fontFamily = poppins_light)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                TextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = {
                        Text(
                            text = context.getString(R.string.password),
                            style = TextStyle(fontFamily = poppins_light)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    visualTransformation = PasswordVisualTransformation()
                )

                TextField(
                    value = passwordConfirm.value,
                    onValueChange = { passwordConfirm.value = it },
                    label = {
                        Text(
                            text = context.getString(R.string.confirm_password),
                            style = TextStyle(fontFamily = poppins_light)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    visualTransformation = PasswordVisualTransformation()
                )

                MyButton(
                    text = context.getString(R.string.signup),
                    onClick = {
                        scope.launch {
                            val registerRequest = RegisterRequest(
                                firstname.value.text,
                                lastname.value.text,
                                email.value.text,
                                username.value.text,
                                password.value.text
                            )
                            onRegisterClick(registerRequest, context, navController)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    componentType = ComponentTypes.Primary
                )


//                Button(
//                    onClick = {
//                        navController.navigate(Routes.SignUp.route)
//                        scope.launch {
//                            val registerRequest = RegisterRequest(
//                                firstname.value.text,
//                                lastname.value.text,
//                                email.value.text,
//                                username.value.text,
//                                password.value.text
//                            )
//                            onRegisterClick(registerRequest)
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(10.dp),
//                    shape = RoundedCornerShape(10.dp),
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Purple700)
//                ) {
//                    Text(
//                        text = "Sign up",
//                        style = TextStyle(color = Color.White, fontFamily = poppins_medium)
//                    )
//                }
            }
        }
    )
}

private suspend fun onRegisterClick(
    registerRequest: RegisterRequest,
    context: Context,
    navController: NavHostController
) {
    val ugeOverflowApiService = ApiService.init()
    val sessionManager = SessionManagerSingleton.sessionManager
    CoroutineScope(Dispatchers.IO).launch {
        val response = ugeOverflowApiService.registerUser(registerRequest)
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                Log.d("register Ok", sessionManager.getToken().toString())
                Toast.makeText(context, "Successfully registered", Toast.LENGTH_SHORT).show();
                navController.navigate(Routes.Login.route)
            } else {
                Log.d("register error", sessionManager.getToken().toString())
                Toast.makeText(
                    context,
                    "Error " + response.code() + " " + response.message(),
                    Toast.LENGTH_SHORT
                ).show(); //(must be handle)
            }
        }
    }


}





