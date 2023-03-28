package fr.uge.ugeoverflow.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.api.OneQuestionResponse
import fr.uge.ugeoverflow.api.QuestionResponse
import fr.uge.ugeoverflow.model.Question
import fr.uge.ugeoverflow.model.Tag
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.ui.screens.question.QuestionItem
import fr.uge.ugeoverflow.ui.screens.question.QuestionListItem
import fr.uge.ugeoverflow.ui.theme.Blue300
import getTagsFromDB

@Composable
fun TagDetails(navController: NavHostController, tagName: String) {
    val server = ApiService.init()
    var questions by remember { mutableStateOf(emptyList<OneQuestionResponse>()) }
    val  tags = getTagsFromDB()
    val matchingTag = tags.find { tag -> tag.tagType == tagName }
    val tagDescription = matchingTag?.description ?: "No description found"

    LaunchedEffect(Unit) {
        try {
            val response = server.getQuestionsByTag(tagName = tagName)
            if (response.isSuccessful) {
                questions = response.body() ?: emptyList()
            }
        }
        catch (e: Exception) {}
    }

    Column( Modifier.padding(15.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Questions tagged [${tagName}]", fontSize = 24.sp, modifier = Modifier.weight(.7F))
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(backgroundColor = Blue300),
                contentPadding = PaddingValues(3.dp, 2.dp),
                modifier = Modifier.weight(.3F)
            ) {
                Text(
                    modifier = Modifier.clickable { navController.navigate("AskQuestion") },
                    text = "Add Question",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.button.copy(fontSize = 10.sp, color = Color.White)
                )

            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .size(10.dp))

        Text(text = tagDescription, fontSize = 12.sp)
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .size(13.dp))
        Text(text = "${questions.size} questions", fontSize = 17.sp)
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .size(10.dp))
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 15.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(questions) { question ->
                    QuestionItem(navController, question)
                }
            }
        }
    }
}
