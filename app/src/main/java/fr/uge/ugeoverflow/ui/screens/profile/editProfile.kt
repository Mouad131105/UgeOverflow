package fr.uge.ugeoverflow.ui.screens.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import coil.Coil
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberImagePainter
import coil.executeBlocking
import coil.request.ImageRequest
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.AddressDTO
import fr.uge.ugeoverflow.api.UpdateProfileDTO
import fr.uge.ugeoverflow.api.UserProfileDTO
import fr.uge.ugeoverflow.services.ProfileService
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
            image.value = imageBit.value?.asImageBitmap()
            ProfileService.uploadImage(
                name = SessionManagerSingleton.sessionManager.getUsername().toString(),
                imageBit = imageBit.value!!,
                onSuccess = { name ->
                    // A success message like image uploaded successfully
                    Log.i("EditProfilePage", name)
                    Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT)
                        .show()
                },
                onError = {
                    Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
                }
            )
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
        mutableStateOf(
            user.value.address
//            AddressDTO(
//                user.value.address?.street,
//                user.value.address?.city,
//                user.value.address?.zipCode,
//                user.value.address?.country
//            ).toString()
        )
    }

    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }


    //Ne fonctionne pas

//    val launcher2 = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent(),
//    ) { uri ->
//        // Load the image from the gallery URI using Coil
//        val request = ImageRequest.Builder(context)
//            .data(uri)
//            .build()
//        val result = Coil.imageLoader(context).executeBlocking(request)
//
//        // Update the image bitmap state with the loaded bitmap
//        imageBitmap.value = result.drawable?.toBitmap()?.asImageBitmap()
//    }


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
                        if (image.value == null) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Filled.Person,
                                contentDescription = "Edit",
                                modifier = Modifier
                                    .size(150.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {
                                        launcher.launch("image/*")
                                    }
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
//                        Surface(
//                            modifier = Modifier.fillMaxWidth(),
//                            color = MaterialTheme.colors.background
//                        ) {
//                            imageBitmap.value?.let {
//                                Image(
//                                    bitmap = it,
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .size(150.dp)
//                                        .clip(CircleShape)
//                                        .align(Alignment.CenterHorizontally)
//                                        .clickable {
//                                            launcher.launch("image/*")
//                                            onClosed()
//                                        },
//                                    contentScale = ContentScale.Crop,
//                                )
//                            }
//                            Button(
//                                onClick = { launcher2.launch("image/*") },
//                                modifier = Modifier
//                                    .wrapContentSize()
//                                    .padding(16.dp)
//                            ) {
//                                Text("Select Image")
//                            }
//                        }
                        TextField(
                            value = firstName.value,
                            onValueChange = { firstName.value = it },
                            label = { Text(text = "First Name") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                        TextField(
                            value = lastName.value,
                            onValueChange = { lastName.value = it },
                            label = { Text(text = "Last Name") },
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
                        address.value?.let { it ->
                            TextField(
                                value = it,
                                onValueChange = { address.value = it },
                                label = { Text(text = "Address") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            )
                        }

                        MyButton(
                            text = "Save",
                            componentType = ComponentTypes.SuccessOutline,
                            onClick = {
                                ProfileService.updateProfile(
                                    UpdateProfileDTO(
                                        username = username.value,
                                        bio = bio.value,
                                        email = email.value,
                                        firstName = firstName.value,
                                        lastName = lastName.value,
                                        address = address.value,
                                        profilePicture = imageBit.value.toString()
                                    ),
                                    onSuccess = { it ->
                                        user.value = it
                                        onClosed()
                                    },
                                    onError = {
                                        onClosed()
                                    }
                                )
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

@Composable
fun GalleryImage(context: Context) {
    // Create a mutable state for the selected image bitmap
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    // Create a launcher for the gallery picker
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri ->
        // Load the image from the gallery URI using Coil
        val request = ImageRequest.Builder(context)
            .data(uri)
            .build()
        val result = Coil.imageLoader(context).executeBlocking(request)

        // Update the image bitmap state with the loaded bitmap
        imageBitmap = result.drawable?.toBitmap()?.asImageBitmap()
    }

    // Show the selected image bitmap, or a button to select an image
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        if (imageBitmap != null) {
            Image(
                painter = rememberImagePainter(imageBitmap!!),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
            ) {
                Text("Select Image")
            }
        }
    }
}
