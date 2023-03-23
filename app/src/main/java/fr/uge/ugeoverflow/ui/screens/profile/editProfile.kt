package fr.uge.ugeoverflow.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.UserProfileDTO

@Composable
fun EditProfilePage(
    user: MutableState<UserProfileDTO>,
    image: MutableState<ImageBitmap?>,
    navController: NavController,
    onClosed: () -> Unit
) {

    val username = remember {
        mutableStateOf(user.value.username)
    }
    val bio = remember {
        mutableStateOf(user.value.bio)
    }
    val email = remember {
        mutableStateOf(user.value.email)
    }
//    val password = remember {
//        mutableStateOf(user.value.)
//    }
//    val confirmPassword = remember {
//        mutableStateOf(user.value.password)
//    }
    val firstName = remember {
        mutableStateOf(user.value.firstName)
    }
    val lastName = remember {
        mutableStateOf(user.value.lastName)
    }
    val address = remember {
        mutableStateOf(user.value.address)
    }


    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = {
            onClosed()
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // User Information Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        image.value?.let {
                            Image(
                                bitmap = it,
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.CenterHorizontally),
                                contentScale = ContentScale.Crop
                            )
                        }
                        TextField(
                            value = username.value,
                            onValueChange = { username.value = it },
                            label = { Text(text = "Username") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                        TextField(
                            value = bio.value!!,
                            onValueChange = { bio.value = it },
                            label = { Text(text = "Bio") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                        TextField(
                            value = address.value!!,
                            onValueChange = { address.value = it },
                            label = { Text(text = "Address") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    )
}
