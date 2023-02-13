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
import androidx.compose.ui.text.style.TextAlign
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
            .height(100.dp),
        elevation = 2.dp,
        backgroundColor = Color(0xFFE7ECF4),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {

        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier
                    .weight(0.25F)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)){

                    Row {
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
                }

                Row(modifier = Modifier
                    .weight(0.65F)
                    .fillMaxWidth()){
                    Column(modifier = Modifier.fillMaxWidth()) {
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
                                text = "${it.take(128)}...", fontSize = 12.sp)
                        }
                    }


                }
                Row(modifier = Modifier
                    .weight(0.14F)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                    Column(modifier = Modifier
                        .weight(0.5F)
                        .fillMaxHeight()){
                        Row{
                            question.getTags?.let {
                                for (tag in it) {

                                    Text(
                                        text = tag.getTAG_TYPE.toString(),
                                        fontSize = 12.sp,
                                        modifier = Modifier.background(color = Color.Yellow)
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                    )



                                }
                            }
                        }

                    }
                    Column(modifier = Modifier
                        .weight(0.5F)
                        .fillMaxHeight()
                        .background(Color.LightGray)) {
                        Text("${question.getVotes.size} ${if (question.getVotes.isEmpty()) stringResource(R.string.vote) else stringResource(R.string.votes)}" +
                                "     ${question.getAnswers?.size} ${stringResource(R.string.answers)}", fontSize = 12.sp, modifier = Modifier.padding(start=30.dp))
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
            .padding(4.dp)
            .size(28.dp)
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
    )
}





