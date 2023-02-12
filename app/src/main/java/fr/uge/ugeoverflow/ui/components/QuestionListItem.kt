package fr.uge.ugeoverflow.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugeoverflow.model.Question
import fr.uge.ugeoverflow.R




@Composable
fun QuestionListItem(question : Question) {

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(120.dp),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {

        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier
                    .weight(0.25F)
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(horizontal = 16.dp)){

                    Row {
                        userImage(question = question)
                        Text(
                            text = "UserName",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        )
                    }
                }

                Row(modifier = Modifier
                    .weight(0.65F)
                    .fillMaxWidth()){
                    Row {
                        question.getTitle?.let { Text(text = it) }
                    }
                    }
                Row(modifier = Modifier
                    .weight(0.15F)
                    .fillMaxWidth()
                    .background(Color.Green)
                    .padding(horizontal = 16.dp)){
                    Column(modifier = Modifier
                        .weight(0.5F)
                        .fillMaxHeight()){
                        question.getTags?.let {   Text(it.map { tag -> tag.getTAG_TYPE }.joinToString(" ")) }
                    }
                    Column(modifier = Modifier
                        .weight(0.5F)
                        .fillMaxHeight()
                        .background(Color.LightGray)) {
                            Box{
                                Text("${question.getVotes.size} ${if (question.getVotes.isEmpty()) stringResource(R.string.vote) else stringResource(R.string.votes)}" +
                                        "     ${question.getAnswers?.size} ${stringResource(R.string.answers)}", fontSize = 12.sp)
                            }

                    }

                }
            }

        }
    }
}

@Composable
private fun userImage(question: Question) {
    Image(
        painter = painterResource(id = R.drawable.user2),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(40.dp)
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
    )
}



