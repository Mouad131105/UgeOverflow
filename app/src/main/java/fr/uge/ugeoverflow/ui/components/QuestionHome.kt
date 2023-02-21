package fr.uge.ugeoverflow.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.uge.ugeoverflow.SessionManager.SessionManager
import fr.uge.ugeoverflow.data.QuestionsDataProvider

@Composable
fun QuestionsHome(sessionManager: SessionManager) {
    val questions = remember {QuestionsDataProvider.questionLists}

    if(sessionManager.isUserLoggedIn.value) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = sessionManager.getTokenrfromSharedPref()?: "No Token")
        }
    }

    LazyColumn(contentPadding = PaddingValues(horizontal = 6.dp,vertical = 15.dp ) ){
        items(
            items = questions,
            itemContent = {
                QuestionListItem(question=it)
            }
        )
    }
}