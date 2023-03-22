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
    navController: NavController
) {
    var rating by remember { mutableStateOf(user.value.reputation) }
//    val maxScore = 10
//    val numStars = 10
//    val selectedStars = remember {
//        mutableStateListOf<Boolean>().apply {
//            for (i in 0 until numStars) {
//                add(i < rating)
//            }
//        }
//    }


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
                                .clickable {
                                    rating = i
                                },
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null,
                            tint = if (rating >= i) Color.Yellow else Color.Gray,
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
