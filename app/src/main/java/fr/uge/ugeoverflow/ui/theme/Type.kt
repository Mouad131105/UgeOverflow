package fr.uge.ugeoverflow.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fr.uge.ugeoverflow.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

// L'AJOUT DU FONT PERSONALISE
val poppins_semibold = FontFamily(Font(R.font.poppins_semibold))
val poppins_bold = FontFamily(Font(R.font.poppins_bold))
val poppins_light = FontFamily(Font(R.font.poppins_light))
val poppins_medium = FontFamily(Font(R.font.poppins_medium))