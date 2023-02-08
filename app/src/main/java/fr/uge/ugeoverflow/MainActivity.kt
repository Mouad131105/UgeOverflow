package fr.uge.ugeoverflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import fr.uge.ugeoverflow.ui.components.QuestionsHome
import fr.uge.ugeoverflow.ui.theme.UGEoverflowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UGEoverflowTheme{
                myApp()

            }

        }
    }
}

@Composable
fun myApp(){
    Scaffold(
        content = {
            QuestionsHome()
        }
    )
}