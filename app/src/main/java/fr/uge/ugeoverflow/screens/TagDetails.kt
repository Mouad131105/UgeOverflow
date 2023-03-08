package fr.uge.ugeoverflow.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import fr.uge.ugeoverflow.model.Question
import fr.uge.ugeoverflow.model.Tag
import fr.uge.ugeoverflow.ui.components.QuestionListItem

@Composable
fun TagDetails(tag: Tag, questions: List<Question>) {
    Column {
        Text(text = "Questions tagged [${tag.getTAG_TYPE}]", fontWeight = FontWeight.Bold)
        Text(text = tag.getDescription.toString())
        LazyColumn {
            items(questions) { question ->
                QuestionListItem(question = question)
            }
        }
    }
}
