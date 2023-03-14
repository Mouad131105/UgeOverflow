package fr.uge.ugeoverflow.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*

import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.api.*
import fr.uge.ugeoverflow.ui.routes.Routes
import fr.uge.ugeoverflow.services.LoginService
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyButton
import fr.uge.ugeoverflow.ui.theme.poppins_light
import fr.uge.ugeoverflow.ui.theme.poppins_medium

@Composable
fun LoginPage(navController: NavHostController) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString("Sign up here"),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = { navController.navigate(Routes.SignUp.route) },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = poppins_medium,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colors.primary
            )
        )
    }
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        Text(text = "Login", style = TextStyle(fontSize = 40.sp, fontFamily = poppins_medium))

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Username", fontFamily = poppins_light) },
            value = username.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { username.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Password", fontFamily = poppins_light) },
            value = password.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            MyButton(
                text = "Login",
                onClick = {
                    Log.i("user", username.value.text + " " + password.value.text)
                    LoginService.login(
                        LoginRequest(username.value.text, password.value.text),
                        {
                            Toast.makeText(
                                context,
                                "Login successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate("Questions")
                        },
                        {
                            Toast.makeText(
                                context,
                                "Invalid login credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) },
                componentType = ComponentTypes.Primary,
                modifier = Modifier.fillMaxWidth(),
            )
//            Button(
//                onClick = {
//                    Log.i("user", username.value.text + " " + password.value.text)
//                    LoginService.login(
//                        LoginRequest(username.value.text, password.value.text),
//                        {
//                            Toast.makeText(
//                                context,
//                                "Login successful",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            navController.navigate("Questions")
//                        },
//                        {
//                            Toast.makeText(
//                                context,
//                                "Invalid login credentials",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    )
//                },
//                shape = RoundedCornerShape(5.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//            ) {
//                Text(text = "Login", fontFamily = poppins_medium)
//            }

        }

        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = { navController.navigate(Routes.ForgotPassword.route) },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = poppins_light
            )
        )
    }


}

fun tesd(email: String = "infzoe", password: String = "infzoe") {
    Log.i("user", email + " " + password)
}
