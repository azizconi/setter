package aziz.ibragimov.setter.ui.screen.main.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import aziz.ibragimov.setter.R
import azizjon.ibragimov.setter.data.local.music.Music
import aziz.ibragimov.setter.ui.theme.RobotoFonts
import com.example.setter.ui.theme.TextColor


@Composable
fun MusicItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    music: Music
) {

    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    onClick()
                }
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        var uri by remember {
            mutableStateOf(music.albumartUri)
        }


        Row(
            modifier = modifier
        ) {
            Box(
                modifier = modifier.size(50.dp)
            ) {
//                if (GetAlbumartImage.init(music.albumId, context) == null) {
//                    Icon(
//                        painter = rememberAsyncImagePainter(
//                            model = Uri.parse("android.resource://${context.packageName}/${R.drawable.ic_baseline_music_note_24}"),
//                        ),
//                        contentDescription = null,
//                        modifier = modifier
//                            .clip(RoundedCornerShape(100))
//                            .size(50.dp)
//                            .zIndex(1f),
//                        tint = Color.Black
//                    )
//                } else {
                Image(

                    painter = rememberAsyncImagePainter(
                        model = uri,
                        onError = {
                            uri =
                                Uri.parse("android.resource://${context.packageName}/${R.drawable.ic_baseline_music_note_icon_app}")
                        }
                        /*"https://sefon.pro/img/artist_photos/jony.jpg"*/
                    ),
                    contentDescription = null,
                    modifier = modifier
                        .clip(RoundedCornerShape(100))
                        .size(50.dp),
                    contentScale = ContentScale.Crop
                )
//                }


            }


            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .padding(start = 30.dp)
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(/*0.85f*/0.98f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = /*"Комета"*/music.title,
                    fontSize = 16.sp,
                    color = TextColor,
                    fontFamily = RobotoFonts,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier,
                )

                Text(
                    text = /*"Jony"*/music.author,
                    fontSize = 12.sp,
                    fontFamily = RobotoFonts,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle()
                )
            }
        }


        Box(
            modifier = modifier
                .size(26.dp)
        )
//        IconButton(
//            onClick = { /*TODO*/ },
//            modifier = modifier
//                .size(26.dp)
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.menu),
//                contentDescription = null,
//            )
//        }

    }

}


@Composable
fun PlayingMusicItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    onClick()
                }
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = modifier
        ) {
            Box() {
                Image(
                    painter = rememberAsyncImagePainter(model = "https://sefon.pro/img/artist_photos/jony.jpg"),
                    contentDescription = null,
                    modifier = modifier
                        .clip(RoundedCornerShape(100))
                        .size(50.dp)
                )

            }


            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .padding(start = 30.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Комета",
                    fontSize = 16.sp,
                    color = TextColor,
                    fontFamily = RobotoFonts,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle()
                )

                Spacer(modifier = modifier.height(5.dp))

                Text(
                    text = "Jony",
                    fontSize = 12.sp,
                    fontFamily = RobotoFonts,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle()
                )
            }
        }


        IconButton(
            onClick = { onCancelClick() },
            modifier = modifier
                .size(26.dp)
        ) {
            Icon(
                Icons.Default.Cancel,
                contentDescription = null,
            )
        }

    }

}


