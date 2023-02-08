package fr.uge.ugeoverflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugeoverflow.model.Question


@Composable
fun QuestionListItem(question : Question) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth().height(100.dp),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(0.75F).fillMaxHeight().background(Color.Red)) {
                Row(modifier = Modifier.weight(0.8F).fillMaxWidth()){
                    Column(modifier = Modifier.weight(0.2F).fillMaxHeight().background(Color.Yellow)){
                        Text("user and image here")
                    }
                    Column(modifier = Modifier.weight(0.8F).fillMaxHeight().background(Color.Blue)){
                        question.getTitle?.let { Text(text = it) }
                    }
                }
                Row(modifier = Modifier.weight(0.2F).fillMaxWidth().background(Color.Green).padding(horizontal = 16.dp)){
                    question.getTags?.let {   Text(it.map { tag -> tag.getTAG_TYPE }.joinToString(" ")) }
                }
            }
            Column(modifier = Modifier.weight(0.17F).fillMaxHeight().background(Color.Gray).padding(horizontal = 4.dp, vertical = 23.dp).align(alignment = CenterVertically)) {
                Text("0 votes",fontSize = 12.sp)
                Text("3 answers",fontSize = 12.sp)
                Text("2 votes ",fontSize = 12.sp)

            }
        }
    }
}



