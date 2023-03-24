package fr.uge.ugeoverflow.ui.screens.question

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Warning
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.*
import fr.uge.ugeoverflow.filters.QuestionFilterType
import fr.uge.ugeoverflow.filters.QuestionsFilterManager
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.services.MailService

import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.components.*
import fr.uge.ugeoverflow.ui.theme.White200
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


@Composable
fun QuestionListItem(question: QuestionResponse) {

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenHeightDp.dp / 5.5f),
        elevation = 2.dp,
        backgroundColor = Color(0xFFE7ECF4),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .weight(0.25F)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                userImage()
                Text(
                    text = "UserName",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color(0xFF604969)
                    ),
                    modifier = Modifier.padding(5.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.65F)
            ) {
                question.title?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(start = 5.dp),
                        fontWeight = FontWeight.W800,
                        color = Color(0xFF4552B8),
                        fontSize = 15.sp
                    )
                }
                question.body?.let {
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = "${it.take(128)}...", fontSize = 12.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(bottom = 6.dp)
                        .align(CenterVertically)
                ) {
                    question.tags?.let {
                        for (tag in it) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(
                                        White200
                                    )
                            ) {
                                Text(
                                    text = tag.toString(),
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                }
                /*Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterVertically)
                ) {
                    Text(

                                "     ${question.ans?.size} ${stringResource(R.string.answers)}",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 30.dp),
                        color = Color.Gray
                    )
                }*/
            }
        }
    }
}

@Composable
fun userImage() {
    val imageList =
        remember { listOf(R.drawable.user1, R.drawable.user2, R.drawable.user3, R.drawable.user4) }
    val randomImageId = remember { imageList.random() }
    Image(
        painter = painterResource(id = randomImageId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(4.dp)
            .size(28.dp)
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
    )
}

@Composable
fun AllQuestionsScreen( navController :NavController,filterOption: String) {
    val ugeOverflowApiSerivce = ApiService.init()
    var questions by remember { mutableStateOf(emptyList<OneQuestionResponse>()) }
    var questionsToFilter by remember { mutableStateOf(emptyList<OneQuestionResponse>()) }


    val questionsFilterManager = QuestionsFilterManager()
    questionsFilterManager.init()

    LaunchedEffect(filterOption) {
        Log.i("",filterOption)

        try {
            // get questions with the selected filter option from backend
            val selectedFilter = QuestionFilterType.valueOf(filterOption)
            if (selectedFilter == QuestionFilterType.ALL) {

                val response = ugeOverflowApiSerivce.getAllQuestionsDto()
                if (response.isSuccessful) {
                    questions = response.body() ?: emptyList()
                    questionsToFilter = questions
                    Log.d(response.code().toString(), response.body().toString())
                } else {
                    Log.d(response.code().toString(), response.message())
                }
            } else {
                Log.i("i",selectedFilter.name)
                questions = questionsFilterManager.getQuestionsByFilter(selectedFilter.name, questionsToFilter)

            }
        } catch (e: Exception) {
            throw e.message?.let { ApiException(e.hashCode(), it) }!!
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 15.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(questions) { question ->
                QuestionItem(navController,question)
            }
        }
    }
}

@Composable
fun QuestionItem(navController: NavController ,question: OneQuestionResponse) {
    val context = LocalContext.current.applicationContext
    val sessionManager = SessionManagerSingleton.sessionManager
    MyCard(
        modifier = Modifier
            .fillMaxWidth(),
        cardType = ComponentTypes.LightOutline,
        header = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(28.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
                )
                ClickableText(
                    text = AnnotatedString(question.title),
                    modifier = Modifier.padding(start = 5.dp),
                    style = MaterialTheme.typography.caption.copy(
                        color = MaterialTheme.colors.secondary
                    ),
                    onClick = { navController.navigate("${Routes.OneQuestion.route}/${question.id}") },

                )
                Icon(
                    Icons.Filled.Warning,
                    contentDescription = "Signal",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { MainScope().launch {
                            try {
                                if(sessionManager.isUserLoggedIn.value){
                                    MailService.sendEmailToNotifyAdmin(question = question)
                                    Toast.makeText(context, "Signaled to admin!", Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(context, "You should be logged in to signal a question!", Toast.LENGTH_LONG).show()
                                    navController.navigate(Routes.Login.route)
                                }

                            } catch (e: Exception) {
                                Toast.makeText(context, "Oops, something gone wrong", Toast.LENGTH_LONG).show()
                            }

                        } }
                )




            }
        },
        body = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "${question.body.take(128)}...", fontSize = 12.sp
                )
            }
        },
        footer = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(bottom = 6.dp)
                        .align(CenterVertically)
                ) {
                    question.tags.let {
                        for (tag in it) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(White200)
                            ) {
                                Text(
                                    text = tag,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .align(CenterVertically)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                if (question.answersCounter > 0) Color.Green else Color.Transparent,
                                RoundedCornerShape(4.dp)
                            )
                    ) {
                        Text(
                            text = "${question.answersCounter} ${stringResource(R.string.answers)}",
                            fontSize = 12.sp,
                            color = if (question.answersCounter > 0) Color.White else Color.Black,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(start = 30.dp),
                    ){
                        Text(
                            text = "asked " + question.getTimePassedSinceQuestionCreation(question.creationTime),
                            fontSize = 12.sp,

                            color = Color.Gray
                        )
                    }


                }


                }

        }
    )
}

@Composable
fun FilterButtons(
    filters: List<String>,
    selectedFilter: String?,
    onFilterSelected: (String) -> Unit,
) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        for (filter in filters) {
            Button(
                onClick = { onFilterSelected(filter) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (filter == selectedFilter) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                ),
                modifier = Modifier.padding(end = 8.dp),
            ) {
                Text(text = filter)
            }
        }
    }
}








