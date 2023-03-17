import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import fr.uge.ugeoverflow.api.AnswerResponse
import fr.uge.ugeoverflow.api.CommentResponse
import fr.uge.ugeoverflow.api.OneQuestionResponse

import androidx.navigation.NavHostController

import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.utils.Utils

import kotlinx.coroutines.runBlocking

import coil.compose.rememberImagePainter
import fr.uge.ugeoverflow.ui.components.*


@Composable
fun QuestionScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val question = getQuestionById("")

    val sortedAnswers = question.answers.sortedByDescending { it.creationTime }

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
                        QuestionCard(question=question, navController)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${sortedAnswers.size} Answers",
                                style = MaterialTheme.typography.h5
                            )
                            // Dropdown menu for selecting the sorting criteria
                            var expanded by remember { mutableStateOf(false) }
                            var selectedOption by remember { mutableStateOf(Utils.SortOption.CreationTime) }

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
                    items(sortedAnswers.size) { index ->
                        AnswerCard(answer = sortedAnswers[index], navController)

                    }
                }
            }
        },

    )
}


@Composable
fun QuestionCard(question:OneQuestionResponse, navController:NavHostController){
    MyCard(
        modifier = Modifier.fillMaxWidth(),
        header = {
            Text(
                text = question.title,
                style = MaterialTheme.typography.h5
            )
        },
        body = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = question.body,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

            if (question.tags.isNotEmpty()) {
                Row(modifier = Modifier.padding(bottom = 4.dp)) {
                    question.tags.forEach { tag ->
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
                    text =  "${Utils.formatDateUsingTimeAgo(question.creationTime)} by",
                    style = MaterialTheme.typography.caption,
                )
                Spacer(Modifier.width(4.dp))
                ClickableText(
                    text = AnnotatedString(question.user.username),
                    style = MaterialTheme.typography.caption.copy(
                        color = MaterialTheme.colors.secondary
                    ),
                    onClick = { navController.navigate("user/${question.user.id}") },
                )
                croppedImageFromDB(question.user.profilePicture)

            }
        },
        cardType = ComponentTypes.Secondary
    )
}
@Composable
fun croppedImageFromDB(imageData:String){
    Image(
        painter = rememberImagePainter(data = imageData, builder = {
            placeholder(R.drawable.user4) // in case image not available
            error(R.drawable.user3) // in case there is an error
        }),
        contentDescription="",
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
fun AnswerCard(answer: AnswerResponse, navController:NavHostController) {
    var expanded by remember { mutableStateOf(false) }
    val answerLines = answer.body.lines()
    MyCard(
        modifier = Modifier.fillMaxWidth(),
        body = {
            Column {
                answerLines.take(if (expanded) answerLines.size else 2).forEachIndexed { index, line ->
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
                    onClick = { navController.navigate("user/${answer.user.id}") },
                )
                croppedImageFromDB(answer.user.profilePicture)

            }
        },
        cardType = ComponentTypes.SecondaryOutline
    )
}






fun getQuestionById(questionId: String): OneQuestionResponse = runBlocking {
    //TODO : Receive ID from getQuestionById  when clicking on it
    val response = ApiService.init().getQuestion("ce2db5a6-00db-4735-96fc-be79c1fe80fd")
    Log.d("response ", response.message())
    if (response.isSuccessful){
        Log.d("response ", response.message())
    }
    response.body() ?: throw RuntimeException("Failed to fetch question")

}

