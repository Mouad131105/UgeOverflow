package fr.uge.ugeoverflow.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugeoverflow.model.Question
import fr.uge.ugeoverflow.model.Tag
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.ui.components.QuestionListItem
import fr.uge.ugeoverflow.ui.theme.Blue200
import fr.uge.ugeoverflow.ui.theme.Blue300

@Composable
fun TagDetails(tag: Tag, questions: List<Question>) {
    Column( Modifier.padding(15.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Questions tagged [${tag.getTAG_TYPE}]", fontSize = 24.sp, modifier = Modifier.weight(.7F))
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(backgroundColor = Blue300),
                contentPadding = PaddingValues(3.dp, 2.dp),
                modifier = Modifier.weight(.3F)
            ) {
                Text(
                    text = "Add Question",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.button.copy(fontSize = 10.sp, color = Color.White)
                )
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .size(10.dp))
        Text(text = tag.getDescription.toString(), fontSize = 12.sp)
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .size(10.dp))
        LazyColumn {
            items(questions) { question ->
                QuestionListItem(question = question)
            }
        }
    }
}
