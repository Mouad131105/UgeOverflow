package fr.uge.ugeoverflow.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.uge.ugeoverflow.api.ApiException
import fr.uge.ugeoverflow.api.OneQuestionResponse
import fr.uge.ugeoverflow.api.UserBoxDTO
import fr.uge.ugeoverflow.model.User
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.ui.screens.profile.FollowCard
import fr.uge.ugeoverflow.ui.screens.question.userImage
import java.util.*

@Composable
fun UserView(user: UserBoxDTO, navController: NavController, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
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
                        text = user.username,
                        style = MaterialTheme.typography.h5
                    )
                    val address = user.address
                    Text(
                        text = "${address.city ?: ""}, ${address.country ?: ""}, ${address.zipCode ?: ""}",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                val r = "${Routes.Profile.route}/${user.username}"
                                Log.d("UserCard", r)
                                navController.navigate(r)
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun UserListScreen(navController: NavController) {
    val ugeOverflowApiSerivce = ApiService.init()
    var users by remember { mutableStateOf(emptyList<UserBoxDTO>()) }

    LaunchedEffect(Unit) {
        try {
            val response = ugeOverflowApiSerivce.getUsersDto()
            if (response.isSuccessful) {
                users = response.body() ?: emptyList()
                Log.d(response.code().toString(), response.body().toString())
            } else {
                Log.d(response.code().toString(), response.message())
            }
        } catch (e: Exception) {
            throw e.message?.let { ApiException(e.hashCode(), it) }!!
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(users) { user ->
            UserView(user = user, navController, onItemClick = {

//                navController.navigate("${Routes.UserDetails.route}/${user.username}")
            })
        }
    }
}


