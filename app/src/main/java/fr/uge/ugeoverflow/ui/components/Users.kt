package fr.uge.ugeoverflow.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.uge.ugeoverflow.model.User
import fr.uge.ugeoverflow.ui.routes.Routes
import fr.uge.ugeoverflow.ui.screens.question.userImage
import java.util.*

@Composable
fun UserView(user: User, navController: NavController, onItemClick: (UUID) -> Unit) {
    Column (modifier = Modifier.fillMaxHeight(1f).clickable { onItemClick(user.id)}){
        Card (modifier = Modifier.fillMaxWidth().height(LocalConfiguration.current.screenHeightDp.dp / 6.5f)){
            Column{
                Row (modifier = Modifier.padding(top = 10.dp, start = 10.dp)){
                    Box (modifier = Modifier.fillMaxHeight(0.8f).fillMaxWidth(0.2f)){userImage()}
                    Column (modifier = Modifier.padding(top = 10.dp, start = 20.dp)){
                        Text(text = user.username)
                        Text(text = user.address.getCity.toString()+", "+user.address.getCountry.toString())
                        user.score?.let { Text(text = it.toString())}
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
@Composable
fun UserListScreen(users: List<User>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(users) { user ->
            UserView(user = user, navController,onItemClick = {
                navController.navigate("${Routes.UserDetails.route}/${user.id}")
            })
        }
    }
}


