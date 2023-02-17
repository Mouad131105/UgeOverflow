package fr.uge.ugeoverflow.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.components.CustomTopAppBar
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.ui.theme.Purple700
import fr.uge.ugeoverflow.ui.theme.poppins_light
import fr.uge.ugeoverflow.ui.theme.poppins_medium


@Composable
fun SignUp(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBar(navController)
    }
}

@Composable
fun ScaffoldWithTopBar(navController: NavHostController) {
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
                    onClick = {navController.navigate(Routes.SignUp.route)  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Purple700)
                ) {
                    Text(text = "Sign up", style = TextStyle(color = Color.White, fontFamily = poppins_medium))
                }
            }
}