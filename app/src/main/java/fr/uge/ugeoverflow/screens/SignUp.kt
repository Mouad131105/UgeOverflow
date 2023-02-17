package fr.uge.ugeoverflow.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.api.RegisterRequest
import fr.uge.ugeoverflow.api.UgeOverflowApi
import fr.uge.ugeoverflow.components.CustomTopAppBar
import fr.uge.ugeoverflow.model.User
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.ui.theme.Purple700
import fr.uge.ugeoverflow.ui.theme.poppins_light
import fr.uge.ugeoverflow.ui.theme.poppins_medium
import kotlinx.coroutines.*
import java.time.Duration


@Composable
fun SignUp(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBar(navController)
    }
}

@Composable
fun ScaffoldWithTopBar(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val context= LocalContext.current;
    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = scaffoldState.snackbarHostState),
        topBar = {
            CustomTopAppBar(navController, "Signup", true)
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

                Text(text = "Sign up", style = TextStyle(fontSize = 40.sp, fontFamily = poppins_medium))

                TextField(  value = lastname.value,
                    onValueChange = { lastname.value = it },
                    label = { Text(text = "lastname", style = TextStyle(fontFamily = poppins_light)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp) )

                TextField(  value = firstname.value,
                    onValueChange = { firstname.value = it },
                    label = { Text(text = "firstname", style = TextStyle(fontFamily = poppins_light)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp) )

                TextField(
                    value = username.value,
                    onValueChange = { username.value = it },
                    label = { Text(text = "Username", style = TextStyle(fontFamily = poppins_light)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                TextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text(text = "Email", style = TextStyle(fontFamily = poppins_light)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp)
                )

                TextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text(text = "Password", style = TextStyle(fontFamily = poppins_light)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    visualTransformation = PasswordVisualTransformation()
                )

                TextField(
                    value = passwordConfirm.value,
                    onValueChange = { passwordConfirm.value = it },
                    label = { Text(text = "Confirm password", style = TextStyle(fontFamily = poppins_light)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    visualTransformation = PasswordVisualTransformation()
                )

                Button(
                    onClick = {navController.navigate(Routes.SignUp.route)
                            scope.launch {
                                val registerRequest = RegisterRequest(firstname.value.text, lastname.value.text, email.value.text, username.value.text, password.value.text)
                                onRegisterClick( context, registerRequest )
                                Log.e("test", "test");
                            }
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Purple700)
                ) {
                    Text(text = "Sign up", style = TextStyle(color = Color.White, fontFamily = poppins_medium))
                }
            }
        })
}

private suspend fun onRegisterClick(context: Context, registerRequest: RegisterRequest) {
    val ugeOverflowApiService = UgeOverflowApi.create()
    val responseFlag = mutableStateOf(false);
        CoroutineScope(Dispatchers.IO).launch {
        val response = ugeOverflowApiService.registerUser(registerRequest)
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                Toast.makeText(context, "Successfully registered", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(context, "Error "+response.code() +" "+ response.message(), Toast.LENGTH_SHORT).show(); //(must be handle)
            }
        }
    }


}





