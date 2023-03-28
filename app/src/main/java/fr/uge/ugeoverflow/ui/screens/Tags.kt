import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.TagResponse
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.session.ApiService
import kotlinx.coroutines.runBlocking

@Composable
fun TagScreen(navController: NavHostController) {


    val tags = getTagsFromDB()

    var searchFilter by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchFilter,
            onValueChange = { searchFilter = it },
            label = { Text(stringResource(id = R.string.search_tag) ) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        TagList(tags = tags, filter = searchFilter, navController = navController)
    }
}

@Composable
fun TagList(tags: List<TagResponse>, filter: String,  navController: NavHostController) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(tags.filter { it.tagType?.contains(filter, ignoreCase = true) ?: false }) { tag ->
            Tag(tag = tag,  navController = navController)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Tag(tag: TagResponse,  navController: NavHostController) {
    var showTooltip by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(15.dp)
            .border(
                1.dp,
                Color(android.graphics.Color.parseColor("#DBDDDE")),
                RoundedCornerShape(4.dp)
            )
            .clickable(onClick = {
                // Toggle the tooltip visibility on click
                showTooltip = true
            })
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
            ) {
                // Add a tagtype text with a background color
                Text(
                    text = tag.tagType ?: "",
                    style = TextStyle(
                        color = Color(android.graphics.Color.parseColor("#89CDF9")),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center

                    ),
                    modifier = Modifier
                        .background(
                            Color(android.graphics.Color.parseColor("#DAEFFC")),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(7.dp)
                        .width(IntrinsicSize.Min)
                        .height(IntrinsicSize.Min)
                        .clickable {
                            showTooltip = true
                        }
                )

            }

            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Add a text with the tag description
            Text(
                text = tag.description ?: "",
                color = Color.Black,
                fontSize = 13.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = " ${tag.questionCount} questions",
                color = Color.Black,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,

                )
        }
        if (showTooltip) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the tooltip
                    showTooltip = false
                },
                title = {
                    Text(text = tag.tagType ?: "" ,  color = Color(android.graphics.Color.parseColor("#89CDF9") )
                        ,fontWeight = FontWeight.Bold
                        , fontSize = 16.sp )
                },
                text = {
                    Column {
                        Text(text = tag.description ?: "")
                    }
                },
                dismissButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(android.graphics.Color.parseColor("#E8E8E8") )),
                        onClick = {
                            // Dismiss the tooltip
                            showTooltip = false

                        }
                    ) {
                        Text(text = "Cancel"  ,color = Color(android.graphics.Color.parseColor("#89CDF9")) )

                    }
                },
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(android.graphics.Color.parseColor("#89CDF9") )),
                        onClick = {

                            navController.navigate("${Routes.TagDetails.route}/${tag.tagType}")

                        }
                    ) {
                        Text(text = "See questions"  ,color = Color.White )

                    }
                }
            )
        }

    }
}


fun getTagsFromDB(): List<TagResponse> = runBlocking {
    val response = ApiService.init().getAllTags()
    val tags = response.body() ?: throw RuntimeException("Failed to fetch question Do")
    tags.sortedByDescending { it.questionCount }
}


