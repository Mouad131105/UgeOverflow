package fr.uge.ugeoverflow.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.UserBoxDTO
import fr.uge.ugeoverflow.routes.Routes
import java.util.*


@Composable
fun FollowsModal(
    name: String,
    follow: List<UserBoxDTO> = listOf(
        UserBoxDTO(
            UUID.randomUUID(),
            "user1",
            "email",
            "url"
        ),
        UserBoxDTO(
            UUID.randomUUID(),
            "user2",
            "email",
            "url"
        ),
        UserBoxDTO(
            UUID.randomUUID(),
            "user3",
            "email",
            "url"
        ),
        UserBoxDTO(
            UUID.randomUUID(),
            "user4",
            "email",
            "url"
        ),
        UserBoxDTO(
            UUID.randomUUID(),
            "user1",
            "email",
            "url"
        ),
        UserBoxDTO(
            UUID.randomUUID(),
            "user2",
            "email",
            "url"
        ),
        UserBoxDTO(
            UUID.randomUUID(),
            "user3",
            "email",
            "url"
        ),
        UserBoxDTO(
            UUID.randomUUID(),
            "user4",
            "email",
            "url"
        )
    ),
    onClose: () -> Unit = {},
    navController: NavController
) {
    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = onClose,
        content = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(500.dp)
                    .padding(16.dp),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = name,
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.End
                        ) {

                            IconButton(onClick = onClose) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null
                                )
                            }
                        }

                    }
                    Divider(
                        color = Color(0xFFDDDDDD),
                        thickness = 1.dp,
                        modifier = Modifier
                            .padding(top = 2.dp, bottom = 2.dp)
                            .padding(start = 16.dp, end = 16.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(follow) { follower ->
                            FollowCard(follower = follower, navController)
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun FollowCard(
    follower: UserBoxDTO,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
            .clickable {
                val r = "${Routes.Profile.route}/${follower.username}"
                Log.d("FollowCard", r)
                navController.navigate(r)
            },
        elevation = 4.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
//                painter = painterResource(follower.profilePicture.),
                painter = painterResource(R.drawable.user1),
                contentDescription = null,
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = follower.username,
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = follower.email,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}
