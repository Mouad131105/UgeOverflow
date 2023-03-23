package fr.uge.ugeoverflow.ui.screens.question


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.api.QuestionResponse
import fr.uge.ugeoverflow.ui.components.ComponentSize
import fr.uge.ugeoverflow.ui.components.ComponentTypes
import fr.uge.ugeoverflow.ui.components.MyButton

@Composable
fun QuestionsHomeScreen(navController: NavHostController) {

//    val questions = remember { QuestionsDataProvider.questionLists}
//    LazyColumn(contentPadding = PaddingValues(horizontal = 6.dp,vertical = 15.dp ) ){
//        items(
//            items = questions,
//            itemContent = {
//                QuestionListItem(question=it)
//            }
//        )
//    }


    AllQuestionsScreen()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {
        MyButton(
            text = "Ask Question",
            onClick = {
                navController.navigate("AskQuestion")
            },
            componentType = ComponentTypes.Primary,
            componentSize = ComponentSize.Small,
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        MyButton(
            text = "Go to One Question",
            onClick = {
                navController.navigate("OneQuestion")
            },
            componentType = ComponentTypes.Dark,
            componentSize = ComponentSize.Small,
        )

    }




    //from db
    // AllQuestionsScreen()
//    QuestionForm(navController)
//    Log.e("USER CONNECTED ?",SessionManagerSingleton.sessionManager.getToken().toString() )
}


