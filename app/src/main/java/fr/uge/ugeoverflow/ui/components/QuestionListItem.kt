package fr.uge.ugeoverflow.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import fr.uge.ugeoverflow.model.Question
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.*
import fr.uge.ugeoverflow.model.Tag
import fr.uge.ugeoverflow.routes.Routes
import fr.uge.ugeoverflow.services.UgeOverflowApiService
import fr.uge.ugeoverflow.ui.theme.White200
import kotlinx.coroutines.launch

@Composable
fun QuestionListItem(question: Question) {

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenHeightDp.dp / 5.5f),
        elevation = 2.dp,
        backgroundColor = Color(0xFFE7ECF4),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .weight(0.25F)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                userImage(question = question)
                Text(
                    text = "UserName",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color(0xFF604969)
                    ),
                    modifier = Modifier.padding(5.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.65F)
            ) {
                question.getTitle?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(start = 5.dp),
                        fontWeight = FontWeight.W800,
                        color = Color(0xFF4552B8),
                        fontSize = 15.sp
                    )
                }
                question.getContent?.getText?.let {
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = "${it.take(128)}...", fontSize = 12.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(bottom = 6.dp)
                        .align(CenterVertically)
                ) {
                    question.getTags?.let {
                        for (tag in it) {
                            Log.d("tag05", tag.toString())
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(
                                        White200
                                    )
                            ) {
                                Text(
                                    text = tag.getTag.toString(),
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterVertically)
                ) {
                    Text(
                        "${question.getVotes.size} ${
                            if (question.getVotes.isEmpty()) stringResource(
                                R.string.vote
                            ) else stringResource(R.string.votes)
                        }" +
                                "     ${question.getAnswers?.size} ${stringResource(R.string.answers)}",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 30.dp),
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun userImage(question: Question?) {
    Image(
        painter = painterResource(id = R.drawable.user2),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(4.dp)
            .size(28.dp)
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
    )
}
@Composable
fun AllQuestionsScreen() {
    val ugeOverflowApiSerivce = UgeOverflowApi.createWithAuth()
    val coroutineScope = rememberCoroutineScope()

    var questions by remember { mutableStateOf(emptyList<QuestionResponse>()) }
    Log.i("ugeOverflowApiSerivce", ugeOverflowApiSerivce.toString())
    // Use the LaunchedEffect to execute the API call once and update the UI
    LaunchedEffect(Unit) {
        try {
            val response = ugeOverflowApiSerivce.getAllQuestions()
            if (response.isSuccessful) {
                questions = response.body() ?: emptyList()
                Log.d(response.code().toString(), response.body().toString())
            } else {
                Log.d(response.code().toString(), response.message())
            }
        } catch (e: Exception) {
            throw e.message?.let { ApiException(e.hashCode(), it) }!!
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("All Questions") }) },
        content = {
            LazyColumn(contentPadding = PaddingValues(horizontal = 6.dp,vertical = 15.dp ) ){
                items(questions) { question ->
                    QuestionItem(question)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                coroutineScope.launch {
                    val result = mutableStateOf(false)
                    result.value = true

                }
            }) {
                Icon(Icons.Default.Add, "Ask Question")
            }
        }

    )
}

@Composable
fun QuestionForm(navController:NavHostController) {
    val ugeOverflowApiSerivce = UgeOverflowApi.createWithAuth()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val title = remember { mutableStateOf("") }
    val content = remember { mutableStateOf("") }
    val tags = remember { mutableStateOf(listOf(
        Tag("JAVA"), Tag("SPRING"), Tag("ANDROID")
    )) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Ask a Question") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title field
            OutlinedTextField(
                value = title.value,
                onValueChange = { title.value = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            // Content field
            OutlinedTextField(
                value = content.value,
                onValueChange = { content.value = it },
                label = { Text("Content") },
                modifier = Modifier.fillMaxWidth()
            )
            // Tag dropdown menu
            TagDropdownMenu(
                tags = tags.value,
                onSelectionChanged = { tags.value = it }
            )
            // Post button
            Button(
                onClick = {
                    val question = QuestionRequest(title.value, content.value, tags.value)
                    val token = UserSession.getToken()
                    if (token != null) {
                        scope.launch {
                            try {
                                Log.e("Send", question.toString())
                                val response = ugeOverflowApiSerivce.postQuestion("Bearer $token", question)
                                if (response.isSuccessful) {
                                    scaffoldState.snackbarHostState.showSnackbar("Success")
                                } else {
                                    scaffoldState.snackbarHostState.showSnackbar("Failed to post question")
                                }
                            } catch (e: Exception) {
                                scaffoldState.snackbarHostState.showSnackbar("Failed to post question")
                            }
                        }
                    } else {
                        Toast.makeText(context, "User not authenticated",Toast.LENGTH_SHORT)
                        //scaffoldState.snackbarHostState.showSnackbar("User not authenticated")
                        navController.navigate(Routes.Login.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Post")
            }
        }
    }
}
@Composable
fun TagDropdownMenu(
    tags: List<Tag>,
    onSelectionChanged: (List<Tag>) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedTags by remember { mutableStateOf(emptyList<Tag>()) }
    var modifier =
        Modifier
            .clickable(onClick = { expanded = true })
            .background(Color.LightGray)
            .padding(horizontal = 16.dp, vertical = 8.dp)

    Box(modifier = modifier){
        Text(
            text = if (selectedTags.isNotEmpty()) {
                selectedTags.joinToString { it.getTag.toString() }
            } else {
                "Select tags"
            }
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = modifier
    ) {
        tags.forEach { tag ->
            DropdownMenuItem(onClick = {
                if (selectedTags.contains(tag)) {
                    selectedTags = selectedTags.filter { it != tag }
                } else {
                    selectedTags = selectedTags + tag
                }
                onSelectionChanged(selectedTags)
            }) {
                Checkbox(
                    checked = selectedTags.contains(tag),
                    onCheckedChange = null
                )
                tag.getTag?.let { Text(text = it, modifier = Modifier.padding(start = 8.dp)) }
            }
        }
    }
}
@Composable
fun QuestionItem(question: QuestionResponse) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = question.title, fontWeight = FontWeight.Bold)
            Text(text = question.id.toString(), modifier = Modifier.padding(top = 8.dp))
        }
    }
}









