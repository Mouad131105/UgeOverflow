package fr.uge.ugeoverflow.screens

import android.content.Context
import android.graphics.fonts.FontFamily
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.api.*
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.ui.theme.Purple700
import fr.uge.ugeoverflow.ui.theme.poppins_light
import fr.uge.ugeoverflow.ui.theme.poppins_medium
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                color = Purple700
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
            Button(

                onClick = {
                    Log.i("user", username.value.text+" "+password.value.text)
                    onLoginClick(context, navController, LoginRequest(username.value.text,password.value.text))
                          },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Login", fontFamily = poppins_medium)
            }

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

private fun onLoginClick(context: Context, navController:NavHostController, loginRequest: LoginRequest) {
    CoroutineScope(Dispatchers.IO).launch {
        val response = UgeOverflowApi.create().loginUser(loginRequest)
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                Toast.makeText(
                    context,
                    "Login successful",
                    Toast.LENGTH_SHORT
                ).show()
                //save and username token
                response.body()?.data?.let { UserSession.setUserSession(newToken = it) }
                navController.navigate("Questions")
            } else {
                Toast.makeText(
                    context,
                    "Invalid login credentials",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
