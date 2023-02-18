package fr.uge.ugeoverflow.ui.components

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.api.ApiException
import fr.uge.ugeoverflow.api.QuestionResponse
import fr.uge.ugeoverflow.api.UgeOverflowApi
import fr.uge.ugeoverflow.api.UserSession
import fr.uge.ugeoverflow.data.QuestionsDataProvider
import fr.uge.ugeoverflow.services.UgeOverflowApiService

@Composable
fun QuestionsHome(navController: NavHostController){
    /*
    val questions = remember {QuestionsDataProvider.questionLists}
    LazyColumn(contentPadding = PaddingValues(horizontal = 6.dp,vertical = 15.dp ) ){
        items(
            items = questions,
            itemContent = {
                QuestionListItem(question=it)
            }
        )
    }
     */

    //from db
   // AllQuestionsScreen()
    QuestionForm(navController)
    Log.e("USER CONNECTED ?",UserSession.getToken().toString() )
}


