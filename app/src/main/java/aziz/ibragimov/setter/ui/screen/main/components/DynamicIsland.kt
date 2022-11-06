package aziz.ibragimov.setter.ui.screen.main.components

import android.net.Uri
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import aziz.ibragimov.setter.R
import azizjon.ibragimov.setter.data.local.music.Music
import com.example.setter.ui.theme.LineColor
import com.example.setter.ui.theme.Purple1
import com.example.setter.ui.theme.Purple2
import aziz.ibragimov.setter.ui.theme.RobotoFonts
import azizjon.ibragimov.setter.utils.StateDynamicIsland
import azizjon.ibragimov.setter.utils.StateDynamicIslandStack


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DynamicIsland(
    modifier: Modifier = Modifier,
    progress: MutableState<Float>,
    onProgressChangeFinished: (Float) -> Unit,
    onProgressChange: (Float) -> Unit,
    audio: Music?,
    isAudioPlaying: Boolean,
    onStart: (Music) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onClickDynamicIsland: (Boolean) -> Unit,
    state: StateDynamicIsland,
    onShuffled: (Boolean) -> Unit,
    onRepeat: (Boolean) -> Unit,
    dynamicIslandStackStack: StateDynamicIslandStack
) {

    LaunchedEffect(key1 = isAudioPlaying) {
        Log.e("TAG", "DynamicIsland: ${isAudioPlaying}")
    }
    var position by remember {
        mutableStateOf(0f)
    }

    position = progress.value

    var expanded by remember {
        mutableStateOf(false)
    }

    var isRepeat by remember {
        mutableStateOf(false)
    }

    var isShuffle by remember {
        mutableStateOf(false)
    }


    var imageSource by remember(audio?.albumartUri) {
        mutableStateOf(audio?.albumartUri)
    }

    LaunchedEffect(key1 = audio) {
        if (audio != null) {
            expanded = true
            onClickDynamicIsland(true)
        }
    }


    val context = LocalContext.current




    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .widthIn(
                min = /*160.dp*/0.dp
            )
            .defaultMinSize(minHeight = /*30.dp*/0.dp)
            .clip(
                if (expanded) RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp,
                ) else RoundedCornerShape(40.dp)
            )
            .background(Color.Black)
            .clickable(
                onClick = {
                    expanded = !expanded
                    onClickDynamicIsland(expanded)
                },
            )
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = expanded,
                transitionSpec = {
                    fadeIn(animationSpec = tween(150, 150)) with
                            fadeOut(animationSpec = tween(150)) using
                            SizeTransform { initialSize, targetSize ->
                                if (targetState) {
                                    keyframes {
                                        // Expand horizontally first.
                                        IntSize(targetSize.height, initialSize.height) at 150
                                        durationMillis = 300

                                    }
                                } else {
                                    keyframes {
                                        // Shrink vertically first.
                                        IntSize(initialSize.height, targetSize.height) at 150
                                        durationMillis = 300
                                    }
                                }
                            }
                }
            ) { targetExpanded ->

                if (!targetExpanded) {
                    AnimatedContent(
                        targetState = when (state) {
                            is StateDynamicIsland.Hide -> false
                            else -> true
                        },
                        transitionSpec = {
                            fadeIn(animationSpec = tween(150, 150)) with
                                    fadeOut(animationSpec = tween(150)) using
                                    SizeTransform { initialSize, targetSize ->
                                        if (targetState) {
                                            keyframes {
                                                // Expand horizontally first.
                                                IntSize(
                                                    targetSize.height,
                                                    initialSize.height
                                                ) at 150
                                                durationMillis = 300
                                            }
                                        } else {
                                            keyframes {
                                                // Shrink vertically first.
                                                IntSize(
                                                    initialSize.height,
                                                    targetSize.height
                                                ) at 150
                                                durationMillis = 300
                                            }
                                        }
                                    }
                        }
                    ) { targetExpandedDynamicIsland ->


                        when (targetExpandedDynamicIsland) {
                            false -> {
                                Box(
                                    modifier = modifier
                                        .size(
                                            0.dp
                                        )
                                )
                            }
                            else -> {
                                Box(
                                    modifier = modifier
                                        .size(
                                            width = when (dynamicIslandStackStack) {
                                                is StateDynamicIslandStack.Hide -> {
                                                    0.dp
                                                }
                                                is StateDynamicIslandStack.Expand -> {
                                                    160.dp
                                                }
                                                is StateDynamicIslandStack.SearchInputOpen -> {
                                                    30.dp
                                                }
                                            },
                                            height = 30.dp
                                        )
                                ) {
                                    Box(
                                        modifier = modifier
                                            .padding(8.dp)
                                    )
                                }

                            }
                        }

                    }

                } else {

                    Box(
                        modifier = modifier
                            .padding(8.dp)
                    ) {
                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(6.dp)
//                            .height(147.dp)
                        ) {
                            Row(
                                modifier = modifier,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = imageSource,/*"https://sefon.pro/img/artist_photos/jony.jpg"*/
                                        onError = {
                                            Log.e(
                                                "TAG",
                                                "DynamicIsland: ${it.result.throwable.message}",
                                            )
                                            imageSource =
                                                Uri.parse("android.resource://${context.packageName}/${R.drawable.ic_baseline_music_note_24}")
                                        }
                                    ),
                                    contentDescription = null,
                                    modifier = modifier
                                        .size(98.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                )


                                Column(
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(start = 8.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {


                                    Box(
                                        modifier = modifier
                                            .padding(start = 8.dp)
                                    ) {
                                        Text(
                                            text = /*"Jony- Комета"*/audio?.author + "-" + audio?.title,
                                            fontFamily = RobotoFonts,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp,
                                            style = TextStyle(),
                                            color = Color.White,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }


                                    Box(modifier = modifier) {
                                        Slider(
                                            value = position,
                                            valueRange = 0f..100f,
                                            onValueChange = {
                                                position = it
                                                onProgressChange(position)
                                            },
                                            onValueChangeFinished = {
                                                onProgressChangeFinished.invoke(position)
                                            },
                                            colors = SliderDefaults.colors(
                                                activeTrackColor = Purple1,
                                                disabledActiveTrackColor = LineColor.copy(0.31f),
                                                thumbColor = Purple1,
                                                disabledThumbColor = LineColor.copy(0.31f)
                                            ),
                                        )
                                    }


                                    Row(
                                        modifier = modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = modifier
                                                .size(20.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.shuffle),
                                                contentDescription = null,
                                                tint = if (!isShuffle) Color.White else Purple2,
                                                modifier = modifier
                                                    .clickable {
                                                        isShuffle = !isShuffle
                                                        onShuffled(isShuffle)
                                                    }
                                            )
                                        }

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = modifier
                                                .padding(horizontal = 18.dp)
                                        ) {
                                            Box(modifier = modifier.size(27.dp)) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.bxs_skip_next_circle),
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = modifier
                                                        .clickable {
                                                            onPrevious()
                                                        }
                                                )
                                            }


                                            Box(
                                                modifier = modifier
                                                    .padding(horizontal = 16.dp)
                                            ) {
                                                Icon(
                                                    painter = painterResource(id = if (!isAudioPlaying) R.drawable.play else R.drawable.stop),
                                                    contentDescription = null,
                                                    tint = Purple1,
                                                    modifier = modifier
                                                        .size(40.dp)
                                                        .clickable {
                                                            if (audio != null) {
                                                                onStart.invoke(audio)
//                                                                isPlaying = !isPlaying
                                                            }
                                                        }
                                                )
                                            }

                                            Box(modifier = modifier.size(27.dp)) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.bxs_skip_next_circle_1),
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = modifier
                                                        .clickable {
                                                            onNext()
                                                        }
                                                )
                                            }

                                        }

                                        Box(
                                            modifier = modifier
                                                .size(20.dp)
                                                .clickable {
                                                    isRepeat = !isRepeat
                                                    onRepeat(isRepeat)
                                                }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.repeat),
                                                contentDescription = null,
                                                tint = if (!isRepeat) Color.White else Purple2
                                            )
                                        }
                                    }
                                }

                            }
                        }

                    }
                }


            }

        }
    }
}
