package fr.uge.ugeoverflow.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MyCard(
    modifier: Modifier = Modifier,
    header: @Composable (() -> Unit)? = null,
    body: @Composable () -> Unit,
    footer: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    cardType: ComponentType = ComponentTypes.Light  // ComponentTypes.Light or ComponentTypes.Dark
) {
    val cardColor = cardType.color
    val contentColor = cardType.contentColor

    Card(
        modifier = modifier
            .padding(vertical = 8.dp)
            .clickable(
                enabled = onClick != null,
                onClick = onClick ?: {}
            ),
        elevation = 8.dp,
        backgroundColor = cardColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(16.dp),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                if (header != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
//                    .padding(16.dp)
                    ) {
                        header()
                        Divider(
                            color = Color(0xFFDDDDDD),
                            thickness = 1.dp,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = if (footer != null) 0.dp else 4.dp)
                    ) {
                        body()
                    }
                }

                if (footer != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
//                            .padding(horizontal = 0.dp, vertical = 8.dp)
                    ) {
                        Divider(
                            color = Color(0xFFDDDDDD),
                            thickness = 1.dp,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                        footer()
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun trygh() {
    MyCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        header = { Text(text = "Header") },
        body = { Text(text = "Body") },
        footer = {
            Column {

                Text(text = "Footer")
                Row {

                    MyButton(
                        text = "Click",
                        onClick = { /*TODO*/ },
                        componentType = ComponentTypes.WarningOutline,
                        componentSize = ComponentSize.Small
                    )
                    MyButton(
                        text = "Click",
                        componentType = ComponentTypes.PrimaryOutline,
                        onClick = { /*TODO*/ }
                    )
                    MyButton(
                        text = "Click",
                        componentType = ComponentTypes.Primary,
                        onClick = { /*TODO*/ },
                        componentSize = ComponentSize.Large
                    )
                }
                Row {

                    MyTag(text = "C++", componentType = ComponentTypes.Secondary)
                    MyTag(
                        text = "C++",
                        componentType = ComponentTypes.Secondary,
                        componentSize = ComponentSize.Small
                    )
                    MyTag(
                        text = "C++",
                        componentType = ComponentTypes.Primary,
                        componentSize = ComponentSize.Large
                    )
                }
            }
        },
        onClick = { /* handle click here */ },
        cardType = ComponentTypes.Secondary
    )
}
