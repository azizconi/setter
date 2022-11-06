package aziz.ibragimov.setter.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import aziz.ibragimov.setter.R

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


val RobotoFonts = FontFamily(
    Font(R.font.roboto_bold, weight = FontWeight.Bold),
    Font(R.font.roboto_thin, weight = FontWeight.Thin),
    Font(R.font.roboto_light, weight = FontWeight.Light),
    Font(R.font.roboto_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.roboto_medium, weight = FontWeight.Medium),
    Font(R.font.roboto_regular, weight = FontWeight.Normal, style = FontStyle.Normal)
)

val NotoSansFonts = FontFamily(
    Font(R.font.noto_sans_bold, weight = FontWeight.Bold),
    Font(R.font.noto_sans_medium, weight = FontWeight.Medium),
    Font(R.font.noto_sans_regular, weight = FontWeight.Normal),
    Font(R.font.noto_sans_semi_bold, weight = FontWeight.SemiBold),
)

val QuattrocentoSansFonts = FontFamily(
    Font(R.font.quattrocento_sans_bold, weight = FontWeight.Bold),
    Font(R.font.quattrocento_sans_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(R.font.quattrocento_sans_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
)