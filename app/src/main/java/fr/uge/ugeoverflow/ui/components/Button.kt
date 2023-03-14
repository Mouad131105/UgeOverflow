package fr.uge.ugeoverflow.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    onClick: () -> Unit,
    componentType: ComponentType,
    componentSize: ComponentSize = ComponentSize.Medium,
    active: Boolean = true,
    content: @Composable (() -> Unit)? = null
) {

    MyButtonComponent(
        modifier = modifier,
        content = {
            if (content == null) {
                Text(
                    text = text!!,
                    fontSize = componentSize.size.value.sp,
                    color = componentType.contentColor
                )
            } else {
                Column {
                    content()
                }
            }
        },
        onClick = onClick,
        componentType = componentType,
        componentSize = componentSize,
        active = active,
        border = false
    )
}

/**Examples**/

@Preview(showBackground = true)
@Composable
fun ButtonPrimary(
    text: String = "Primary Button",
    onClick: () -> Unit = {}
) {
    MyButton(
        text = text,
        onClick = onClick,
        componentType = ComponentTypes.Dark,
        componentSize = ComponentSize.Medium
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonPrimaryOutline(
    text: String = "Primary Outline Button",
    onClick: () -> Unit = {}
) {
    MyButton(
        text = text,
        onClick = onClick,
        componentType = ComponentTypes.PrimaryOutline,
        componentSize = ComponentSize.Medium
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonSecondary(
    text: String = "Secondary Button",
    onClick: () -> Unit = {}
) {
    MyButton(
        text = text,
        onClick = onClick,
        componentType = ComponentTypes.Secondary,
        componentSize = ComponentSize.Medium
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonSecondaryOutline(
    text: String = "Secondary Outline Button",
    onClick: () -> Unit = {}
) {
    MyButton(
        text = text,
        onClick = onClick,
        componentType = ComponentTypes.SecondaryOutline,
        componentSize = ComponentSize.Medium
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonInfo(
    text: String = "Info Button",
    onClick: () -> Unit = {}
) {
    MyButton(
        text = text,
        onClick = onClick,
        componentType = ComponentTypes.Info,
        componentSize = ComponentSize.Medium
    )
}


@Preview(showBackground = true)
@Composable
fun ButtonInfoOutline(
    text: String = "Info Outline Button",
    onClick: () -> Unit = {}
) {
    MyButton(
        text = text,
        onClick = onClick,
        componentType = ComponentTypes.InfoOutline,
        componentSize = ComponentSize.Medium
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonSuccess(
    text: String = "Success Button",
    onClick: () -> Unit = {}
) {
    MyButton(
        text = text,
        onClick = onClick,
        componentType = ComponentTypes.Success,
        componentSize = ComponentSize.Medium
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonSuccessOutline(
    text: String = "Success Outline Button",
    onClick: () -> Unit = {}
) {
    MyButton(
        text = text,
        onClick = onClick,
        componentType = ComponentTypes.SuccessOutline,
        componentSize = ComponentSize.Medium
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonWarning(
    text: String = "Warning Button",
    onClick: () -> Unit = {}
) {
    MyButton(
        text = text,
        onClick = onClick,
        componentType = ComponentTypes.Warning,
        componentSize = ComponentSize.Medium
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonWarningOutline(
    text: String = "Warning Outline Button",
    onClick: () -> Unit = {}
) {
    MyButton(
        text = text,
        onClick = onClick,
        componentType = ComponentTypes.WarningOutline,
        componentSize = ComponentSize.Medium
    )
}

@Preview(showBackground = true)
@Composable
fun tryyy() {
    val text: String = "Warning Outline Button"
    val onClick: () -> Unit = {}
    Column {
        MyButton(
            text = text,
            onClick = onClick,
            componentType = ComponentTypes.WarningOutline,
            componentSize = ComponentSize.Medium
        )
        MyButton(
            text = text,
            onClick = onClick,
            componentType = ComponentTypes.PrimaryOutline,
            componentSize = ComponentSize.Small
        )
        MyButton(
            text = text,
            onClick = onClick,
            componentType = ComponentTypes.DangerOutline,
            componentSize = ComponentSize.Large
        )
    }

}