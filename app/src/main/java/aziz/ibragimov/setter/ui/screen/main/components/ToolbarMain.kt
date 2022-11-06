package aziz.ibragimov.setter.ui.screen.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import aziz.ibragimov.setter.R
import aziz.ibragimov.setter.ui.theme.NotoSansFonts
import aziz.ibragimov.setter.ui.theme.QuattrocentoSansFonts
import aziz.ibragimov.setter.ui.theme.RobotoFonts
import com.example.setter.ui.theme.*

@Composable
fun ToolbarMain(
    modifier: Modifier = Modifier,
    getSearchText: (String) -> Unit,
    isSearchInputOpen: (Boolean) -> Unit
) {

    val focusManager = LocalFocusManager.current

    var isOpenSearchInput by remember {
        mutableStateOf(false)
    }

    val searchText = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TopAppBar(
            backgroundColor = Color.White,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(61.dp),
            elevation = 0.dp,
        ) {
            if (!isOpenSearchInput) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                ) {

                    Text(
                        text = context.getString(R.string.app_name),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = QuattrocentoSansFonts,
                        style = TextStyle(),
                        color = Color.Black,
                    )


                    IconButton(
                        modifier = modifier
                            .size(24.dp),
                        onClick = {
                            isOpenSearchInput = true
                        }
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "bottom-sheet",
                            tint = Color.Black
                        )
                    }

                }
                isSearchInputOpen(false)
            } else {
                isSearchInputOpen(true)
                SearchView(
                    state = searchText,
                    placeHolder = "Поиск",
                    onSearch = {

                    },
                    getState = {
                        searchText.value = it
                        getSearchText(searchText.value)
                    },
                    onClose = {
                        isOpenSearchInput = false
                        searchText.value = ""
                        getSearchText(searchText.value)
                    }
                )
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

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    state: MutableState<String>,
    placeHolder: String,
    onSearch: () -> Unit,
    onClose: () -> Unit,
    getState: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = state.value,
        onValueChange = {
            state.value = it
            getState(state.value)
        },
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        textStyle = LocalTextStyle.current.copy(
            color = TextColor,
            fontSize = 16.sp,
            fontFamily = NotoSansFonts,
            fontWeight = FontWeight.Normal
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Go,
            // делает заглавным каждый символ
            capitalization = KeyboardCapitalization.Sentences
        ),
        keyboardActions = KeyboardActions(
            onGo = {
                onSearch()
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        maxLines = 1,
        decorationBox = { innerTextField ->
            Row(
                modifier = modifier
                    .clip(RoundedCornerShape(15.dp))
                    .border(1.dp, Purple1, RoundedCornerShape(15.dp))
                    .background(LightPurple),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Box(
                    Modifier
                        .weight(1f)
                        .padding(
                            start = 38.dp,
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()

                    if (state.value.isEmpty()) {
                        Text(
                            text = placeHolder,
                            color = LightPurple,
                            fontFamily = RobotoFonts,
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }



                IconButton(
                    onClick = {
                        state.value = ""
                        onClose()
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(
                        Icons.Default.Cancel,
                        contentDescription = null,
                        tint = Purple2,
                        modifier = modifier
                            .size(24.dp)
                    )
                }
            }


        }
    )
}
