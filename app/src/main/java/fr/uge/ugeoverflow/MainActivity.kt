package fr.uge.ugeoverflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import fr.uge.ugeoverflow.SessionManager.ApiManager
import fr.uge.ugeoverflow.SessionManager.SessionManager
import fr.uge.ugeoverflow.ui.components.MainComponent
import fr.uge.ugeoverflow.ui.theme.UGEoverflowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UGEoverflowTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DefaultPreview()
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    val sessionManager  = remember { SessionManager(context) }
    val apiManager = remember { ApiManager(context) }
    MainComponent(sessionManager = sessionManager, apiManager = apiManager)
    }


