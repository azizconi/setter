package aziz.ibragimov.setter.ui.screen.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import aziz.ibragimov.setter.R
import aziz.ibragimov.setter.ui.theme.QuattrocentoSansFonts
import com.example.setter.ui.theme.*

@Composable
fun NavBlockMain(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val list = listOf(
        NavBlockItem(
            IconItem(R.drawable.favorite, 35.0, 35.0),
            context.getString(R.string.favorites),
            "",
        ),
        NavBlockItem(
            IconItem(R.drawable.playlist, 37.5, 35.0),
            context.getString(R.string.playlists),
            ""
        ),
        NavBlockItem(
            IconItem(R.drawable.profile, 35.0, 35.0),
            context.getString(R.string.profile),
            ""
        ),
    )


    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(3) {
                NavBlockMainItem(navBlockItemModel = list[it])
            }
        }
        Spacer(modifier = modifier.height(8.dp))
        Box(
            modifier = modifier
                .drawBehind {
                    val strokeWidth = 0.5f * density
                    val y = size.height - strokeWidth / 2

                    drawLine(
                        LineColor,
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
private fun NavBlockMainItem(
    modifier: Modifier = Modifier,
    navBlockItemModel: NavBlockItem
) {


    Column(

        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Purple1)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true),
                onClick = {

                }
            )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = modifier.height(1.dp))
            Box(
                modifier = modifier
                    .size(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = navBlockItemModel.icon.icon),
                    contentDescription = "favorite icon",
                    tint = PrimaryColorSecond,
                    modifier = modifier
                        .width(navBlockItemModel.icon.width.dp)
                        .height(navBlockItemModel.icon.height.dp)
                )
            }

            Text(
                text = navBlockItemModel.title,
                color = TextColor,
                fontSize = 12.sp,
                fontFamily = QuattrocentoSansFonts,
                fontWeight = FontWeight.Bold
            )
        }

    }

}

private data class NavBlockItem(
    val icon: IconItem,
    val title: String,
    val route: String,

    )

private data class IconItem(
    val icon: Int,
    val width: Double,
    val height: Double,
)