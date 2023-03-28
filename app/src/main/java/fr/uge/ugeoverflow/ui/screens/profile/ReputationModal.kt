package fr.uge.ugeoverflow.ui.screens.profile


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.UserProfileDTO
import fr.uge.ugeoverflow.services.ProfileService


@Composable
fun ReputationModal(
    user: MutableState<UserProfileDTO>,
    onDismiss: () -> Unit,
) {
    var rating by remember { mutableStateOf(user.value.reputation) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.select_reputation)) },
        text = {
            Column {
                Text("${stringResource(id = R.string.rating)}: $rating")

                Row(modifier = Modifier.padding(16.dp)) {
                    for (i in 0 until 10) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(
                                    onClick = {
                                        rating = i + 1
                                    }
                                ),
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null,
                            tint = if (rating > i) Color.Yellow else Color.Gray,
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                ProfileService.addReputation(user.value.username, rating,
                    { u ->
                        user.value = u
                        onDismiss()
                    },
                    {
                        onDismiss()
                    }
                )
            }) {
                Text(stringResource(id = R.string.submit))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text( stringResource(id = R.string.cancel) )
            }
        }
    )
}
