package fr.uge.ugeoverflow.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


object ComponentConstants {
    val DefaultHorizontalContentPadding = 16.dp
    val DefaultVerticalContentPadding = 8.dp
}

data class ComponentType(
    val contentColor: Color,
    val color: Color
)

sealed class ComponentSize(val size: Dp) {
    object Small : ComponentSize(12.dp)
    object Medium : ComponentSize(16.dp)
    object Large : ComponentSize(20.dp)
}

@Composable
fun MyButtonComponent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    onClick: () -> Unit,
    componentType: ComponentType,
    componentSize: ComponentSize = ComponentSize.Medium,
    active: Boolean = true,
    border:Boolean = true
) {
    val paddingValues = when (componentSize) {
        ComponentSize.Small -> PaddingValues(
            horizontal = ComponentConstants.DefaultHorizontalContentPadding / 3,
            vertical = ComponentConstants.DefaultVerticalContentPadding / 6
        )
        ComponentSize.Medium -> PaddingValues(
            horizontal = ComponentConstants.DefaultHorizontalContentPadding / 3,
            vertical = ComponentConstants.DefaultVerticalContentPadding / 3
        )
        ComponentSize.Large -> PaddingValues(
            horizontal = ComponentConstants.DefaultHorizontalContentPadding / 3,
            vertical = ComponentConstants.DefaultVerticalContentPadding
        )
        else -> {
            PaddingValues(
                horizontal = ComponentConstants.DefaultHorizontalContentPadding / 3,
                vertical = ComponentConstants.DefaultVerticalContentPadding / 3
            )
        }
    }
    val borderSize=if(border)BorderStroke(1.dp, componentType.contentColor)else null

    Button(
        onClick = onClick,
        enabled = active,
        modifier = modifier.padding(4.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 5.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        shape = RoundedCornerShape(30.dp),
        border = borderSize,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = componentType.color,
            contentColor = componentType.contentColor
        ),
    ) {
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            content()
        }
    }
}



object ComponentTypes {
    val Primary = ComponentType(
        contentColor = Color.White,
        color = Color(0xFF3A71C9)
    )
    val Secondary = ComponentType(
        contentColor = Color(0xFF1A5280),
        color = Color(0xFFE2EAF6)
    )
    val Info = ComponentType(
        color = Color(0xFF55B3D2),
        contentColor = Color.White
    )
    val Success = ComponentType(
        color = Color(0xFF14A34E),
        contentColor = Color.White
    )
    val Warning = ComponentType(
        color = Color(0xFFE3A01B),
        contentColor = Color.White
    )
    val Danger = ComponentType(
        color = Color(0xFFDB4C64),
        contentColor = Color.White
    )
    val Light = ComponentType(
        contentColor = Color(0xFF332D2D),
        color = Color(0xFFFAFAFA)
    )
    val Dark = ComponentType(
        contentColor = Color(0xFFECECEC),
        color = Color(0xFF332D2D)
    )
    val PrimaryOutline = ComponentType(
        contentColor = Color(0xFF3A71C9),
        color = Color.White
    )
    val SecondaryOutline = ComponentType(
        contentColor = Color(0xFF1A5280),
        color = Color.White
    )
    val InfoOutline = ComponentType(
        contentColor = Color(0xFF55B3D2),
        color = Color.White
    )
    val SuccessOutline = ComponentType(
        contentColor = Color(0xFF14A34E),
        color = Color.White
    )
    val WarningOutline = ComponentType(
        contentColor = Color(0xFFE3A01B),
        color = Color.White
    )
    val DangerOutline = ComponentType(
        contentColor = Color(0xFFDB4C64),
        color = Color.White
    )
    val LightOutline = ComponentType(
        contentColor = Color(0xFF332D2D),
        color = Color(0xFFFAFAFA)
    )
    val DarkOutline = ComponentType(
        contentColor = Color(0xFFECECEC),
        color = Color(0xFF332D2D)
    )
}

//@Preview(showBackground = true)
//@Composable
//fun FadingDivider() {
//    Canvas(modifier = Modifier.fillMaxWidth().height(1.dp)) {
//        val gradientColors = listOf(
//            Color.Transparent,
//            Color.Black,
//            Color.Black,
//            Color.Transparent
//        )
//        val gradientStops = listOf(
//            0f,
//            0.3f,
//            0.7f,
//            1f
//        )
//        val gradientShader = LinearGradientShader(
//            startX = 0f,
//            startY = 0f,
//            endX = size.width,
//            endY = 0f,
//            colors = gradientColors,
//            stops = gradientStops,
//            tileMode = TileMode.Clamp
//        )
//        val path = Path().apply {
//            lineTo(size.width, 0f)
//            close()
//        }
//        drawPath(path, Paint().apply {
//            shader = gradientShader
//            style = PaintingStyle.Stroke
//            strokeWidth = 1.dp.toPx()
//        })
//    }
//}
