package com.example.setter.ui.screen.util.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.setter.ui.theme.BorderColor
import aziz.ibragimov.setter.ui.theme.RobotoFonts
import com.example.setter.ui.theme.TextColor
import com.example.setter.ui.theme.TextColor2

@Composable
fun InputCard(
    modifier: Modifier = Modifier,
    size: Modifier,
    placeholder: String,
    title: String,
    require: Boolean,
    singleLine: Boolean,
    fontSizeTitle: Int = 13,
    fontFamilyTitle: FontFamily = RobotoFonts,
    verticalPaddingInput: Int = 0,
    endPaddingInput: Int = 13,
    titleTextColor: Color = TextColor,
    keyBoardIconButton: () -> Unit,
    visualTransformation: () -> VisualTransformation,
    keyboardOptions: () -> KeyboardOptions,
    getTextInput: (String) -> Unit,
    fontSizeForInputText: Int = 14,
    letterSpacing: Double = 0.0,
    borderColor: Color = BorderColor,
    setText: String? = null,
    alignmentText: Alignment.Vertical = Alignment.CenterVertically,
    isTitleShow: Boolean = true,
    enabled: Boolean = true
) {

    var text by rememberSaveable {
        mutableStateOf(setText ?: "")
    }


    Column(
        modifier = modifier
            .padding(bottom = 16.dp)
    ) {


        if (isTitleShow) {
            Row(
                modifier = modifier
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = title,
                    fontFamily = fontFamilyTitle,
                    fontSize = fontSizeTitle.sp,
                    color = titleTextColor,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                )

                if (require) {
                    Text(
                        text = "*",
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = fontSizeTitle.sp,
                    )
                }

            }
        }

        BasicTextField(
            value = text,
            onValueChange = {
                text = it

                getTextInput(text)
            },
            modifier = size,
            singleLine = singleLine,
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
            textStyle = LocalTextStyle.current.copy(
                color = TextColor,
                fontSize = fontSizeForInputText.sp,
                letterSpacing = letterSpacing.em,

                ),
            keyboardOptions = keyboardOptions(),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyBoardIconButton()
                }
            ),
            visualTransformation = visualTransformation(),
            enabled = enabled,
            decorationBox = { innerTextField ->
                Row(
                    modifier = modifier
                        .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
                    verticalAlignment = alignmentText,
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .padding(start = 13.dp, end = endPaddingInput.dp)
                            .padding(vertical = verticalPaddingInput.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (text.isEmpty())
                            Text(
                                text = placeholder,
                                style = LocalTextStyle.current.copy(
                                    color = TextColor2,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal,
                                )
                            )
                        innerTextField()
                    }
                }
            }
        )
    }
}