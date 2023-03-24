package fr.uge.ugeoverflow.ui.screens.question

import android.content.Context
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
import androidx.navigation.NavHostController
import croppedImageFromDB
import fr.uge.ugeoverflow.api.AnswerResponse
import fr.uge.ugeoverflow.api.CommentRequest
import fr.uge.ugeoverflow.api.CommentResponse
import fr.uge.ugeoverflow.api.OneQuestionResponse
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.services.CommentService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyButton
import fr.uge.ugeoverflow.ui.components.MyCard
import fr.uge.ugeoverflow.utils.Utils


@Composable
fun CommentOnFadeModal(
    question: MutableState<OneQuestionResponse>,
    onDismissRequest: () -> Unit,
    answer: AnswerResponse?,
    comments: List<CommentResponse>,
    navController: NavHostController
) {

    val context = LocalContext.current
    var commentText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(550.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    items(comments) { comment ->
                        CommentCard(comment, navController)
                    }
                    item {
                        if (SessionManagerSingleton.sessionManager.isUserLoggedIn.value) {
                            OutlinedTextField(
                                value = commentText,
                                onValueChange = { commentText = it },
                                label = { Text("Add a comment") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            MyButton(
                onClick = {
                    if (SessionManagerSingleton.sessionManager.isUserLoggedIn.value) {
                        if (commentText.isNotEmpty()) {
                            //val location: Location? = LocationService.getLocation(context)
                            val commentRequest =
                                CommentRequest(body = commentText, question.value.id, answer?.id)
                            Log.e("Send", commentRequest.toString())
                            CommentService.addComment(commentRequest,
                                question.value.id, answer?.id,
                                { q ->
                                    question.value = q
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
                            commentText = ""
                        } else {
                            Toast.makeText(context, "Comment cannot be empty", Toast.LENGTH_SHORT)
                                .show()
                        }

                    } else {
                        Toast.makeText(
                            context,
                            "You must be logged in to comment",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        navController.navigate("Login")
                    }
                },
                text = "Post",
                componentType = ComponentTypes.Primary
            )
        },
        dismissButton = {
            MyButton(
                onClick = { onDismissRequest() },
                text = "Close",
                componentType = ComponentTypes.Danger,
                modifier = Modifier.padding(end = 8.dp)
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        shape = MaterialTheme.shapes.medium,
    )
}


@Composable
fun CommentCard(comment: CommentResponse, navController: NavHostController) {
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
                    onClick = {
                        navController.navigate("${Routes.Profile.route}/${comment.user.username}")
                    },
                )
                croppedImageFromDB(comment.user.profilePicture)
            }
        },
        cardType = ComponentTypes.SecondaryOutline
    )
}

@Composable
fun CommentsCard(
    question: MutableState<OneQuestionResponse>,
    navController: NavHostController,
    answer: AnswerResponse?,
    context: Context
) {
    var comments = remember {
        mutableStateOf(question.value.comments)
    }
    val commentDialogOpen = remember { mutableStateOf(false) }
    if (answer != null) {
        comments = remember {
            mutableStateOf(answer.comments)
        }
    }
    fun openCommentDialog() {
        commentDialogOpen.value = true
    }

    // function to close the comment dialog
    fun closeCommentDialog() {
        commentDialogOpen.value = false
    }
    Column {

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
                //TERNARY OPERATOR on comment size or add comment
                text = if (comments.value.isNotEmpty()) "${comments.value.size} comments" else "Add comment",
                modifier = Modifier.clickable {
                    if (SessionManagerSingleton.sessionManager.isUserLoggedIn.value) {
                        openCommentDialog()
                    } else {
                        Toast.makeText(
                            context,
                            "You must be logged in to comment",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        navController.navigate("Login")
                    }

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
                question = question,
                onDismissRequest = { closeCommentDialog() },
                answer = answer, // replace with the correct overflowId
                comments = comments.value,
                navController = navController
            )
        }

    }
    Spacer(modifier = Modifier.height(12.dp))
    Divider(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
        thickness = 2.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}




