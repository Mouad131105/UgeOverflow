package fr.uge.ugeoverflow.ui.screens.profile

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import fr.uge.ugeoverflow.api.UserProfileDTO
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyButton

@Composable
fun EditProfilePage(
    user: MutableState<UserProfileDTO>,
    image: MutableState<ImageBitmap?>,
    navController: NavController,
    onClosed: () -> Unit
) {

    val context = LocalContext.current
    val imageBit = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageBit.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)
        }
    }

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
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {
                                        launcher.launch("image/*")
                                    },
                                contentScale = ContentScale.Crop,

                                )
                        }
                        TextField(
                            value = firstName.value!!,
                            onValueChange = { firstName.value = it },
                            label = { Text(text = "First Name") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                        TextField(
                            value = lastName.value!!,
                            onValueChange = { lastName.value = it },
                            label = { Text(text = "Last Name") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
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

                        MyButton(
                            text = "Save",
                            componentType = ComponentTypes.SuccessOutline,
                            onClick = {
//                                user.value = UserProfileDTO(
//                                    username = username.value,
//                                    bio = bio.value,
//                                    email = email.value,
//                                    firstName = firstName.value,
//                                    lastName = lastName.value,
//                                    address = address.value,
////                                    image = image.value
//                                )
                                onClosed()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        )

                        MyButton(
                            text = "Cancel",
                            componentType = ComponentTypes.DangerOutline,
                            onClick = {
                                onClosed()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        )

                    }
                }
            }
        }
    )
}
