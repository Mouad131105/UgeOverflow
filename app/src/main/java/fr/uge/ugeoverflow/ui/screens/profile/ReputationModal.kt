package fr.uge.ugeoverflow.ui.screens.profile


import android.widget.ProgressBar
import android.widget.RatingBar
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import fr.uge.ugeoverflow.api.*
import fr.uge.ugeoverflow.services.ProfileService
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor


@Composable
fun ReputationModal(
    user: MutableState<UserProfileDTO>,
    onDismiss: () -> Unit,
) {
    var rating by remember { mutableStateOf(user.value.reputation) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Reputation") },
        text = {
            Column {
                Text("Rating: $rating")

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
                Text("Submit")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
