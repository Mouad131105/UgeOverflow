package fr.uge.ugeoverflow.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.UserProfileDTO

@Composable
fun EditProfilePage(user: UserProfileDTO) {
    var username by remember { mutableStateOf(user.username) }
    var bio by remember { mutableStateOf(user.bio ?: "") }
    var address by remember { mutableStateOf(user.address.toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Edit Profile") }
            )
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
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Image(
//                            painter = rememberImagePainter(user.profilePicture),
                            painter = rememberImagePainter(R.drawable.user1),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .align(Alignment.CenterHorizontally)
                        )
                        TextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text(text = "Username") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                        TextField(
                            value = bio,
                            onValueChange = { bio = it },
                            label = { Text(text = "Bio") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                        TextField(
                            value = address,
                            onValueChange = { address = it },
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
