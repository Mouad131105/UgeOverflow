package fr.uge.ugeoverflow.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugeoverflow.model.Question
import fr.uge.ugeoverflow.model.Tag
import fr.uge.ugeoverflow.ui.components.QuestionListItem

@Composable
fun TagDetails(tag: Tag, questions: List<Question>) {
    Column( Modifier.padding(15.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Questions tagged [${tag.getTAG_TYPE}]", fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.fillMaxWidth().size(5.dp),)
        Text(text = tag.getDescription.toString(), fontSize = 10.sp)
        LazyColumn {
            items(questions) { question ->
                QuestionListItem(question = question)
            }
        }
    }
}
