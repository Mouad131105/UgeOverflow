package fr.uge.ugeoverflow.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun AskQuestion(onSubmit: (String, List<String>, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column {
        Text("Ask a question", fontWeight = FontWeight.Bold, fontSize = 24.sp)

        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )

        TextField(
            value = tags,
            onValueChange = { tags = it },
            label = { Text("Tags") }
        )

        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") }
        )

        Button(
            onClick = { onSubmit(title, tags.split(","), content) }
        ) {
            Text("Submit")
        }
    }
}
