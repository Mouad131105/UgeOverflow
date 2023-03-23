package fr.uge.ugeoverflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

object MyError {

    @Composable
    fun ErrorScreen(
        errorMessage: String = "An error has occurred",
        onRetry: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            MyCard(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .background(color = Color.White),
                body = {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Error Icon",
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = "Oops!",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
                        )
                    }
                },
                footer = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        if (onRetry != null) {
                            MyButton(
                                onClick = onRetry,
                                componentType = ComponentTypes.SecondaryOutline,
                            ) {
                                Text(text = "Retry")
                            }
                        }
                        if (onDismiss != null) {
                            MyButton(
                                onClick = onDismiss,
                                componentType = ComponentTypes.Warning,
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text(text = "Dismiss")
                            }
                        }
                    }
                }
            )
        }
    }

}