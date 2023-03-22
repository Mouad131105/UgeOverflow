package fr.uge.ugeoverflow.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MyTag(
    text: String,
    componentType: ComponentType,
    modifier: Modifier = Modifier,
    componentSize: ComponentSize = ComponentSize.Medium,
    onDismiss: (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {

    var dismissed by remember { mutableStateOf(false) }

    MyButtonComponent(
        modifier = modifier,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    fontSize = componentSize.size.value.sp,
                    color = componentType.contentColor,
                    fontFamily = MaterialTheme.typography.body2.fontFamily
                )
                Spacer(modifier = Modifier.width(componentSize.size / 2))
                if (onDismiss != null) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss tag",
                        tint = Color(0xFFAAAAAA),
                        modifier = Modifier
                            .size(componentSize.size + 4.dp)
                            .clickable(onClick = {
                                onDismiss()
                                dismissed = true
                            })
                    )
                }
            }
        },
        onClick = onClick,
        componentType = componentType,
        componentSize = componentSize,
        border = true
    )

}


/**Example of usage*/

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Column {
        MyTag(text = "JAVA", ComponentTypes.InfoOutline, componentSize = ComponentSize.Small)
        MyTag(text = "C", ComponentTypes.WarningOutline, componentSize = ComponentSize.Medium)
        MyTag(text = "C++", ComponentTypes.Info, componentSize = ComponentSize.Large)
    }
}

