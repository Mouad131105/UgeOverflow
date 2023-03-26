import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.AnswerRequest
import fr.uge.ugeoverflow.api.AnswerResponse
import fr.uge.ugeoverflow.api.OneQuestionResponse
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.services.AnswerService
import fr.uge.ugeoverflow.services.QuestionService
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.components.ComponentSize
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyButton
import fr.uge.ugeoverflow.ui.components.MyCard

import fr.uge.ugeoverflow.ui.screens.question.CommentsCard
import fr.uge.ugeoverflow.utils.Utils
import kotlinx.coroutines.runBlocking


object OneQuestionGlobals {
    var questionId: String = ""
}

@Composable
fun QuestionScreen(navController: NavHostController, id: String) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val question = remember { mutableStateOf(getQuestionById(id)) }
    val sortedAnswers = remember(question.value.answers) {
        mutableStateOf(question.value.answers.sortedByDescending { it.creationTime })
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                ) {
                    item {
                        QuestionCard(question = question, navController, context)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(36.dp)
                                    .background(MaterialTheme.colors.secondary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${sortedAnswers.value.size} Answers",
                                style = MaterialTheme.typography.h5
                            )

                            // Dropdown menu for selecting the sorting criteria
                            var expanded by remember { mutableStateOf(false) }
                            var selectedOption by remember { mutableStateOf(Utils.SortOption.CreationTime) }

                            Spacer(modifier = Modifier.width(64.dp))
                            Box(modifier = Modifier.clickable(onClick = { expanded = true })) {
                                Text(
                                    text = "Sort by $selectedOption",
                                    style = MaterialTheme.typography.subtitle2
                                )
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    Utils.SortOption.values().forEach { option ->
                                        DropdownMenuItem(
                                            onClick = {
                                                selectedOption = option
                                                expanded = false
                                            }
                                        ) {
                                            Text(text = option.toString())
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Render the answers in the sorted order
                    items(sortedAnswers.value.size) { index ->
                        AnswerCard(
                            context,
                            answer = sortedAnswers.value[index],
                            question,
                            navController
                        )
                    }
                    // Post new answer
                    item {
                        PostAnswerCard(question, navController)
                    }

                }

            }

        },
    )
}

@Composable
fun PostAnswerCard(question: MutableState<OneQuestionResponse>, navController: NavHostController) {
    val context = LocalContext.current
    var answerText by remember { mutableStateOf("") }

    if (SessionManagerSingleton.sessionManager.isUserLoggedIn.value) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            // add field to add answer and post
            OutlinedTextField(
                value = answerText,
                onValueChange = { answerText = it },
                label = { Text("Add your answer") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MyButton(
                // button on the right
                modifier = Modifier.align(Alignment.CenterEnd),
                text = "Post pour answer",
                onClick = {
                    if (answerText.isNotEmpty()) {
                        val answerRequest = AnswerRequest(answerText, question.value.id)
                        Log.e("Send", answerRequest.toString())
                        val response =
                            AnswerService.addAnswer(answerRequest,
                                question.value.id,
                                { newQuestion ->
                                    question.value = newQuestion
                                    Toast.makeText(
                                        context,
                                        "Answer posted successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //navController.navigate("Question/${OneQuestionGlobals.questionId}")
                                },
                                {
                                    Toast.makeText(
                                        context,
                                        "Failed to post answer",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        answerText = ""
                    }
                },
                componentType = ComponentTypes.Primary,
                componentSize = ComponentSize.Small
            )
        }
    }
}

@Composable
fun QuestionCard(
    question: MutableState<OneQuestionResponse>,
    navController: NavHostController,
    context: Context
) {
    MyCard(
        modifier = Modifier.fillMaxWidth(),
        header = {
            Text(
                text = question.value.title,
                style = MaterialTheme.typography.h5
            )
        },
        body = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = question.value.body,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (question.value.tags.isNotEmpty()) {
                    Row(modifier = Modifier.padding(bottom = 4.dp)) {
                        question.value.tags.forEach { tag ->
                            MyButton(
                                text = tag,
                                onClick = { },
                                componentType = ComponentTypes.LightOutline,
                                componentSize = ComponentSize.Small
                            )
                        }
                    }
                }
            }
        },
        footer = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Asked ",
                    style = MaterialTheme.typography.caption,
                )
                Text(
                    text = "${Utils.formatDateUsingTimeAgo(question.value.creationTime)} by",
                    style = MaterialTheme.typography.caption,
                )
                Spacer(Modifier.width(4.dp))
                ClickableText(
                    text = AnnotatedString(question.value.user.username),
                    style = MaterialTheme.typography.caption.copy(
                        color = MaterialTheme.colors.secondary
                    ),
                    onClick = {
                        navController.navigate("${Routes.Profile.route}/${question.value.user.username}")
                    },
                )
                croppedImageFromDB(question.value.user.profilePicture)

            }
        },
        cardType = ComponentTypes.Secondary
    )
    CommentsCard(question, navController, null, context)


}

@Composable
fun croppedImageFromDB(imageData: String) {
    Image(
        painter = rememberImagePainter(data = imageData, builder = {
            placeholder(R.drawable.user4) // in case image not available
            error(R.drawable.user3) // in case there is an error
        }),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(24.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colors.secondary),
                CircleShape
            )
            .padding(1.dp)
            .clip(CircleShape)

    )
}

@Composable
fun AnswerCard(
    context: Context,
    answer: AnswerResponse,
    question: MutableState<OneQuestionResponse>,
    navController: NavHostController
) {
    val answerDialog = remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val answerLines = answer.body.lines()
    fun showAnswerDialog() {
        answerDialog.value = true
    }

    fun hideAnswerDialog() {
        answerDialog.value = false
    }
    MyCard(
        modifier = Modifier.fillMaxWidth(),
        body = {
            Column {

                answerLines.take(if (expanded) answerLines.size else 2)
                    .forEachIndexed { index, line ->
                        Text(
                            text = line,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = if (expanded || index < 2) Int.MAX_VALUE else 1,
                            modifier = Modifier.clickable { if (!expanded) expanded = true },
                        )
                    }
                if (answerLines.size > 2 && (!expanded || answerLines.size > 3)) {
                    val text = if (expanded) "show less" else "... show more"
                    ClickableText(
                        text = AnnotatedString(
                            text = text,
                            spanStyle = SpanStyle(color = MaterialTheme.colors.secondary),
                        ),
                        onClick = { expanded = !expanded },
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
        },
        footer = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Answered ",
                    style = MaterialTheme.typography.caption,
                )
                Text(
                    text = "${Utils.formatDateUsingTimeAgo(answer.creationTime)} by ",
                    style = MaterialTheme.typography.caption,
                )
                Spacer(Modifier.width(4.dp))
                ClickableText(
                    text = AnnotatedString(answer.user.username),
                    style = MaterialTheme.typography.caption.copy(
                        color = MaterialTheme.colors.secondary
                    ),
                    onClick = {
                        navController.navigate("${Routes.Profile.route}/${answer.user.username}")
                    },
                )
                croppedImageFromDB(answer.user.profilePicture)
            }
        },
        cardType = ComponentTypes.SecondaryOutline,
        onClick = {
            showAnswerDialog()
        }
    )
    if (answerDialog.value) {
        DisplayDialog(context = context, answer = answer, question = question, onDismiss = {
            hideAnswerDialog()
        })
    }
    CommentsCard(question, navController, answer, context)
}


@Composable
fun DisplayDialog(
    context: Context,
    question: MutableState<OneQuestionResponse>,
    answer: AnswerResponse? = null,
    onDismiss: () -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable { onDismiss() }
    ) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Text(text = "Options")
            },
            text = {
                Column {
                    TextButton(onClick = {
                        if (answer == null) {
                            // delete question
                            QuestionService.deleteQuestion(
                                {
                                    Toast.makeText(
                                        context,
                                        "Question deleted successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                {
                                    Toast.makeText(
                                        context,
                                        "Failed to delete question",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            )
                        } else {
                            // delete answer
                            AnswerService.deleteAnswer(
                                {
                                    Toast.makeText(
                                        context,
                                        "Answer deleted successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                {
                                    Toast.makeText(
                                        context,
                                        "Failed to delete answer",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            )
                        }
                    }) {
                        if (SessionManagerSingleton.sessionManager.currentUsername.value == answer?.user?.username) {
                            Text(text = "Delete Answer")
                        } else {
                            Text(text = "Vote Up")
                        }
                    }
                    TextButton(onClick = {
                        if (answer == null) {
                            // edit question
                            /*
                            QuestionService.editQuestion(question.value.id,
                                {
                                    Toast.makeText(
                                        context,
                                        "Question edited successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                {
                                    Toast.makeText(context, "Failed to edit question", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )
                        } else {
                            // edit answer
                            AnswerService.editAnswer(answer,
                                {
                                    Toast.makeText(
                                        context,
                                        "Answer edited successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                {
                                    Toast.makeText(context, "Failed to edit answer", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )

                             */
                        }
                    }) {
                        if (SessionManagerSingleton.sessionManager.currentUsername.value == answer?.user?.username) {
                            Text(text = "Edit Answer")
                        } else {
                            Text(text = "Vote Down")
                        }
                    }
                    /*
                    TextButton(onClick = {
                        if (answer != null) {
                            AnswerService.voteUp(
                                answer.id,
                                {
                                    Toast.makeText(
                                        context,
                                        "Voted up successfully",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                },
                                {
                                    Toast.makeText(context, "Failed to vote up", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )
                            //remove vote up

                        }
                    }) {
                        if (SessionManagerSingleton.sessionManager.currentUsername.value != answer?.user?.username) {
                            Text(text = "Vote Up")
                        }
                    }
                    TextButton(onClick = {
                        if (answer != null) {
                            AnswerService.voteDown(
                                answer.id,
                                {
                                    Toast.makeText(
                                        context,
                                        "Voted down successfully",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                },
                                {
                                    Toast.makeText(context, "Failed to vote", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )
                        }
                    }) {
                        if (SessionManagerSingleton.sessionManager.currentUsername.value != answer?.user?.username) {
                            Text(text = "Vote Down")
                        }
                    }

                     */

                }

            },
            buttons = {
                MyButton(
                    text = "Dismiss",
                    onClick = { onDismiss() },
                    modifier = Modifier.padding(8.dp),
                    componentType = ComponentTypes.DangerOutline,
                    componentSize = ComponentSize.Small
                )

            }
        )
    }
    // close dialog

}

fun getQuestionById(questionId: String): OneQuestionResponse = runBlocking {
    //TODO : Receive ID from getQuestionById  when clicking on it
    val response = ApiService.init().getQuestion(questionId)
    Log.d("response ", response.message())
    if (response.isSuccessful) {
        Log.d("response ", response.message())
    }
    response.body() ?: throw RuntimeException("Failed to fetch question Do")
}


