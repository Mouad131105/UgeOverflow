package fr.uge.ugeoverflow.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugeoverflow.model.Question
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.ApiException
import fr.uge.ugeoverflow.api.QuestionResponse
import fr.uge.ugeoverflow.api.UgeOverflowApi
import fr.uge.ugeoverflow.api.UserSession
import fr.uge.ugeoverflow.ui.theme.White200

@Composable
fun QuestionListItem(question: Question) {

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
                userImage(question = question)
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
                question.getTitle?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(start = 5.dp),
                        fontWeight = FontWeight.W800,
                        color = Color(0xFF4552B8),
                        fontSize = 15.sp
                    )
                }
                question.getContent?.getText?.let {
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
                    question.getTags?.let {
                        for (tag in it) {
                            Log.d("tag05", tag.toString())
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(
                                        White200
                                    )
                            ) {
                                Text(
                                    text = tag.getTAG_TYPE.toString(),
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterVertically)
                ) {
                    Text(
                        "${question.getVotes.size} ${
                            if (question.getVotes.isEmpty()) stringResource(
                                R.string.vote
                            ) else stringResource(R.string.votes)
                        }" +
                                "     ${question.getAnswers?.size} ${stringResource(R.string.answers)}",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 30.dp),
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun userImage(question: Question?) {
    Image(
        painter = painterResource(id = R.drawable.user2),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(4.dp)
            .size(28.dp)
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
    )
}
@Composable
fun AllQuestionsScreen() {
    val ugeOverflowApiSerivce = UgeOverflowApi.createWithAuth(userSession = UserSession)
    val coroutineScope = rememberCoroutineScope()

    var questions by remember { mutableStateOf(emptyList<QuestionResponse>()) }
    Log.i("ugeOverflowApiSerivce", ugeOverflowApiSerivce.toString())
    // Use the LaunchedEffect to execute the API call once and update the UI
    LaunchedEffect(Unit) {
        try {
            val response = ugeOverflowApiSerivce.getAllQuestions()
            if (response.isSuccessful) {
                questions = response.body() ?: emptyList()
                Log.d(response.code().toString(), response.body().toString())
            } else {
                Log.d(response.code().toString(), response.message())
            }
        } catch (e: Exception) {
            throw e.message?.let { ApiException(e.hashCode(), it) }!!
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("All Questions") }) },
        content = {
            LazyColumn(contentPadding = PaddingValues(horizontal = 6.dp,vertical = 15.dp ) ){
                items(questions) { question ->
                    QuestionItem(question)
                }
            }
        }
    )
}

@Composable
fun QuestionItem(question: QuestionResponse) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = question.title, fontWeight = FontWeight.Bold)
            Text(text = question.id.toString(), modifier = Modifier.padding(top = 8.dp))
        }
    }
}









