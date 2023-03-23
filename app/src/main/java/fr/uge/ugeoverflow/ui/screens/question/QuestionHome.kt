package fr.uge.ugeoverflow.ui.screens.question


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.uge.ugeoverflow.api.QuestionResponse
import fr.uge.ugeoverflow.filters.QuestionFilterType
import fr.uge.ugeoverflow.services.MailService
import fr.uge.ugeoverflow.ui.components.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.logging.Level.ALL

@Composable
fun QuestionsHome(navController: NavHostController) {

    var filterOption by remember { mutableStateOf(QuestionFilterType.ALL.getFilterName()) }

    Box(
        modifier = Modifier.fillMaxWidth(),
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
    Column {

        Column(modifier = Modifier.fillMaxSize()) {
            DropdownMenuFilter(onOptionSelected = { option ->
                filterOption = option
            })

            AllQuestionsScreen(navController,filterOption)
        }

    }


}


