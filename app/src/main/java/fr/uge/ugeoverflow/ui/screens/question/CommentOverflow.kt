package fr.uge.ugeoverflow.ui.screens.question

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import croppedImageFromDB
import fr.uge.ugeoverflow.api.CommentRequest
import fr.uge.ugeoverflow.api.CommentResponse
import fr.uge.ugeoverflow.services.CommentService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyButton
import fr.uge.ugeoverflow.ui.components.MyCard
import fr.uge.ugeoverflow.utils.Utils


@Composable
fun CommentOnFadeModal(
    comments: List<CommentResponse>,
    onDismissRequest: () -> Unit,
    overflowId: String,
    navController: NavHostController
) {

    val context = LocalContext.current
    var commentText by remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Comments",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface.apply { copy(alpha = 0.8f) },
                modifier = Modifier.padding(bottom = 10.dp)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .height(500.dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(comments) { comment ->
                        CommentCard(comment)
                    }
                }
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    label = { Text("Add a comment") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    // check if user is authenticated
                    if (SessionManagerSingleton.sessionManager.isUserLoggedIn.value){
                        MyButton(
                            onClick = {
                                if (commentText.isNotEmpty()) {
                                    //val location: Location? = LocationService.getLocation(context)
                                    val commentRequest =
                                        CommentRequest(body = commentText, overflowId = overflowId)

                                    Log.e("Send", commentRequest.toString())
                                    val response =
                                        CommentService.addComment(commentRequest,
                                            overflowId,
                                            {
                                                Toast.makeText(
                                                    context,
                                                    "Comment posted successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                //navController.navigate("Question/${OneQuestionGlobals.questionId}")
                                            },
                                            {
                                                Toast.makeText(
                                                    context,
                                                    "Failed to post comment",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                }
                                commentText = ""
                            },
                            text = "Post",
                            componentType = ComponentTypes.Primary
                        )
                    }
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        confirmButton = {
            MyButton(
                onClick = { onDismissRequest() },
                text = "Close",
                componentType = ComponentTypes.Danger,
                modifier = Modifier.padding(end = 8.dp)
            )
        },
        backgroundColor = MaterialTheme.colors.background,
    )


}


@Composable
fun CommentCard(comment: CommentResponse) {
    MyCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 2.dp),
        body = {
            Column {
                Text(
                    text = comment.body,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        },
        footer = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "${Utils.formatDateUsingTimeAgo(comment.creationTime)} by ",
                    style = MaterialTheme.typography.caption,
                )
                Spacer(Modifier.width(4.dp))
                ClickableText(
                    text = AnnotatedString(comment.user.username),
                    style = MaterialTheme.typography.caption.copy(
                        color = MaterialTheme.colors.secondary
                    ),
                    onClick = { },
                )
                croppedImageFromDB(comment.user.profilePicture)
            }
        },
        cardType = ComponentTypes.SecondaryOutline
    )
}

@Composable
fun CommentsCard(
    comments: List<CommentResponse>,
    navController: NavHostController,
    overflowId: String
) {

    val commentDialogOpen = remember { mutableStateOf(false) }

    fun openCommentDialog() {
        commentDialogOpen.value = true

    }

    // function to close the comment dialog
    fun closeCommentDialog() {
        commentDialogOpen.value = false
    }
    Column {
        if (comments.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (commentDialogOpen.value) Icons.Outlined.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Toggle comment expansion",
                    tint = if (commentDialogOpen.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSurface,
                )
                Text(
                    text = "${comments.size} Comments",
                    modifier = Modifier.clickable {
                        openCommentDialog()
                    },
                    style = MaterialTheme.typography.caption
                )
                Spacer(Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(24.dp)
                        .background(MaterialTheme.colors.secondary)
                )
            }
            Divider(
                color = MaterialTheme.colors.secondary,
                thickness = 2.dp,
                modifier = Modifier
                    .width(100.dp)
                    .align(Alignment.End)
            )

            if (commentDialogOpen.value) {
                CommentOnFadeModal(
                    comments = comments,
                    onDismissRequest = { closeCommentDialog() },
                    overflowId = overflowId, // replace with the correct overflowId
                    navController = navController
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    Divider(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
        thickness = 2.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}


