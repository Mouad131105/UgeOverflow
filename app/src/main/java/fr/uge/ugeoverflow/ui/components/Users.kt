package fr.uge.ugeoverflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.uge.ugeoverflow.model.User
    @Composable
    fun UserView(user: User) {
        Column (modifier = Modifier.fillMaxHeight(1f)){
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
    fun UserList(users: List<User>) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(count = users.size,
                key = {users[it].id}, itemContent = {
                    val user = users[it]
                    UserView(user)
                })
        }
    }