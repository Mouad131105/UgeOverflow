package fr.uge.ugeoverflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import fr.uge.ugeoverflow.screens.ScreenMain
import fr.uge.ugeoverflow.ui.components.AppTopBar
import fr.uge.ugeoverflow.ui.components.MainComponent
import fr.uge.ugeoverflow.ui.components.QuestionsHome
import fr.uge.ugeoverflow.ui.theme.UGEoverflowTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UGEoverflowTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainComponent()
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UGEoverflowTheme {
        QuestionsHome()
    }
}