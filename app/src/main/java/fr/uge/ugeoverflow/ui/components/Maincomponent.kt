package fr.uge.ugeoverflow.ui.components
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun mainComponent(){

}

@Composable
fun AppTopBar(
    onNavItemClick: () -> Unit,
    navController: NavHostController
){
    TopAppBar(
        title = {
            Text(text = "Top App Bar")
        },
        navigationIcon = {
            Button(onClick = { onNavItemClick() }, modifier = Modifier.width(IntrinsicSize.Min)) {
            Icon(Icons.Default.Menu, "Home")
        }},
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = White,
        elevation = 10.dp

    )
}