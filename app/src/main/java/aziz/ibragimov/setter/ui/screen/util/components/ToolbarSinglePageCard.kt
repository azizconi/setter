package com.example.setter.ui.screen.util.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.setter.ui.theme.PrimaryColor
import com.example.setter.ui.theme.PrimaryColorThird
import aziz.ibragimov.setter.ui.theme.QuattrocentoSansFonts

@Composable
fun ToolbarSinglePageCard(
    modifier: Modifier = Modifier,
    title: String,
    navController: NavController,
    onBackIconClick: () -> Unit = {
        navController.popBackStack()
    }
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TopAppBar(
            backgroundColor = Color.White,
            contentPadding = PaddingValues(/*0.dp*/ horizontal = 16.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(61.dp),
            elevation = 0.dp,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = modifier
                        .size(24.dp),
                    onClick = { onBackIconClick() }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "bottom-sheet",
                        tint = PrimaryColorThird
                    )
                }

                Box(
                    modifier = modifier
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = QuattrocentoSansFonts,
                        style = TextStyle(),
                        color = Color.Black,
                    )
                }

            }
        }
        Box(
            modifier = modifier
                .drawBehind {
                    val strokeWidth = 1f * density
                    val y = size.height - strokeWidth / 2

                    drawLine(
                        PrimaryColor,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                }
                .fillMaxWidth()
        )

    }
}