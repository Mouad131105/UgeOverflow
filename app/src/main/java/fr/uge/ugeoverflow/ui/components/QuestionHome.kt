package fr.uge.ugeoverflow.ui.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.session.SessionManager
import fr.uge.ugeoverflow.session.SessionManagerSingleton

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
    Log.e("USER CONNECTED ?",SessionManagerSingleton.sessionManager.getToken().toString() )
}


