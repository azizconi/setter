package com.example.setter.ui.screen.register

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.setter.ui.screen.util.components.ButtonCard
import com.example.setter.ui.screen.util.components.InputCard
import com.example.setter.ui.screen.util.components.ToolbarSinglePageCard

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onCheckPermission: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    Column {
        ToolbarSinglePageCard(navController = navController, title = "Регистрация")


        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = modifier.height(24.dp))
            InputCard(
                size = modifier
                    .fillMaxWidth()
                    .height(40.dp),
                require = false,
                title = "Имя",
                fontSizeTitle = 12,
                keyBoardIconButton = {
                    focusManager.clearFocus()
                },
                visualTransformation = {
                    VisualTransformation.None
                },
                keyboardOptions = {
                    KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    )
                },
                getTextInput = {

                },
                placeholder = "",
                singleLine = true
            )
            Spacer(modifier = modifier.height(6.dp))
            InputCard(
                size = modifier
                    .fillMaxWidth()
                    .height(40.dp),
                require = false,
                title = "Логин",
                fontSizeTitle = 12,
                keyBoardIconButton = {
                    focusManager.clearFocus()
                },
                visualTransformation = {
                    VisualTransformation.None
                },
                keyboardOptions = {
                    KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    )
                },
                getTextInput = {

                },
                placeholder = "",
                singleLine = true
            )
            Spacer(modifier = modifier.height(6.dp))
            InputCard(
                size = modifier
                    .fillMaxWidth()
                    .height(40.dp),
                require = false,
                title = "Пароль",
                fontSizeTitle = 12,
                fontSizeForInputText = 18,
                keyBoardIconButton = {
                    focusManager.clearFocus()
                },
                visualTransformation = {
                    PasswordVisualTransformation()
                },
                keyboardOptions = {
                    KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    )
                },
                getTextInput = {

                },
                placeholder = "",
                singleLine = true,
            )
            Spacer(modifier = modifier.height(6.dp))
            InputCard(
                size = modifier
                    .fillMaxWidth()
                    .height(40.dp),
                require = false,
                title = "Повторите пароль",
                fontSizeTitle = 12,
                fontSizeForInputText = 18,
                keyBoardIconButton = {
                    focusManager.clearFocus()
                },
                visualTransformation = {
                    PasswordVisualTransformation()
                },
                keyboardOptions = {
                    KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    )
                },
                getTextInput = {

                },
                placeholder = "",
                singleLine = true,
            )
            Spacer(modifier = modifier.height(34.dp))
            ButtonCard(
                onClick = {},
                text = "Зарегистрироваться"
            )

        }

    }

}