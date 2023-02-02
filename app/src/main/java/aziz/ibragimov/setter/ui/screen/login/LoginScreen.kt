package aziz.ibragimov.setter.ui.screen.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import aziz.ibragimov.setter.R
import com.example.setter.ui.screen.util.Screen
import com.example.setter.ui.screen.util.components.ButtonCard
import com.example.setter.ui.screen.util.components.InputCard
import aziz.ibragimov.setter.ui.theme.QuattrocentoSansFonts
import aziz.ibragimov.setter.ui.theme.RobotoFonts
import com.example.setter.ui.theme.TextColor
import com.example.setter.ui.theme.TextColor2

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onCheckPermission: () -> Unit
) {
    val context = LocalContext.current


    val focusManager = LocalFocusManager.current

    Column {
        Box(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .padding(top = 60.dp, bottom = 40.dp)
        ) {
            Text(
                text = context.getString(R.string.app_name),
                fontFamily = QuattrocentoSansFonts,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                color = Color.Black
            )
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
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

            Spacer(modifier = modifier.height(15.dp))
            ButtonCard(
                onClick = {
                    onCheckPermission()
//                    navController.navigate(Screen.MainScreen.route)
                },
                text = "Войти"
            )

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 26.dp, bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "или",
                    color = TextColor2,
                    fontFamily = RobotoFonts,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(),
                    fontSize = 12.sp
                )
            }


            Box(
                modifier = modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Зарегистрироваться",
                    color = TextColor,
                    fontFamily = RobotoFonts,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(),
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = modifier.clickable {
                        navController.navigate(Screen.RegisterScreen.route)
                    }
                )

            }

        }
    }

}