package fr.uge.ugeoverflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.uge.ugeoverflow.session.SessionManager
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import fr.uge.ugeoverflow.ui.components.MainComponent
import fr.uge.ugeoverflow.ui.theme.UGEoverflowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManagerSingleton.sessionManager = SessionManager(applicationContext)
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
    MainComponent()

}