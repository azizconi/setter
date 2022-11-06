package com.example.setter.ui.screen.util.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.setter.ui.theme.Purple2
import aziz.ibragimov.setter.ui.theme.RobotoFonts

@Composable
fun ButtonCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Purple2),
        onClick = { onClick() },
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Text(
            text = text,
            fontFamily = RobotoFonts,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = Color.White,
            style = TextStyle()
        )
    }

}