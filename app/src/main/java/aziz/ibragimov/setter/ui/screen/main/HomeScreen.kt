package aziz.ibragimov.setter.ui.screen.main

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import aziz.ibragimov.setter.R
import aziz.ibragimov.setter.data.local.music.Music
import aziz.ibragimov.setter.ui.screen.main.components.DynamicIsland
import aziz.ibragimov.setter.ui.screen.main.components.MusicItem
import aziz.ibragimov.setter.ui.screen.main.components.ToolbarMain
import aziz.ibragimov.setter.ui.theme.QuattrocentoSansFonts
import azizjon.ibragimov.setter.utils.StateDynamicIsland
import azizjon.ibragimov.setter.utils.StateDynamicIslandStack


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    progress: Float,
    onProgressChange: (Float) -> Unit,
    isAudioPlaying: Boolean,
    audioList: List<Music>,
    currentPlayingAudio: Music?,
    onStart: (Music) -> Unit,
    onItemClick: (Music) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onShuffled: (Boolean) -> Unit,
    onRepeat: (Boolean) -> Unit,
    onLongClick: () -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    var dynamicIslandState by remember {
        mutableStateOf<StateDynamicIsland>(StateDynamicIsland.Hide())
    }

    var isSearchInputOpen by remember {
        mutableStateOf(false)
    }

    var isDynamicIslandExpand by remember {
        mutableStateOf(false)
    }

    val alignment by animateAlignmentAsState(if (!isSearchInputOpen) Alignment.TopCenter else Alignment.TopStart)

//    val alignment by animateHorizontalAlignmentAsState(horizontalBias)

    val searchText = remember {
        mutableStateOf("")
    }

    var dynamicIslandStackStack by remember {
        mutableStateOf<StateDynamicIslandStack>(StateDynamicIslandStack.Hide)
    }

    LaunchedEffect(key1 = isSearchInputOpen) {
        if (isSearchInputOpen) {
            if (
                dynamicIslandState is StateDynamicIsland.Expand ||
                dynamicIslandState is StateDynamicIsland.FullExpand
            ) {
                dynamicIslandState = StateDynamicIsland.SearchInputOpen()
                dynamicIslandStackStack = StateDynamicIslandStack.SearchInputOpen
            } else {
                dynamicIslandStackStack = StateDynamicIslandStack.Hide
            }
        } else {
            if (currentPlayingAudio != null) {
                dynamicIslandState = StateDynamicIsland.Expand()
                dynamicIslandStackStack = StateDynamicIslandStack.Expand
            } else {
                dynamicIslandStackStack = StateDynamicIslandStack.Hide
            }
        }

    }


    val context = LocalContext.current

    BottomSheetScaffold(
        sheetContent = {
            Box(modifier = modifier)
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp
    ) {
        Box {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .zIndex(1f)
                    .padding(top = 16.dp, start = if (isSearchInputOpen) 8.dp else 0.dp)
                    .padding(horizontal = 12.dp),
                contentAlignment = alignment
            ) {

                DynamicIsland(
                    progress = remember(progress) {
                        mutableStateOf(progress)
                    },
                    onPrevious = {
                        onPrevious.invoke()
                    },
                    onStart = {
                        onStart.invoke(it)
                    },
                    onNext = {
                        onNext.invoke()
                    },
                    onProgressChangeFinished = onProgressChange,
                    onProgressChange = {

                    },
                    onRepeat = {
                        onRepeat.invoke(it)
                    },
                    onShuffled = {
                        onShuffled.invoke(it)
                    },
                    audio = currentPlayingAudio,
                    isAudioPlaying = isAudioPlaying,
                    onClickDynamicIsland = {
                        dynamicIslandState = if (it) {
                            StateDynamicIsland.FullExpand()
                        } else {
                            if (isSearchInputOpen) {
                                StateDynamicIsland.SearchInputOpen()
                            } else {
                                StateDynamicIsland.Expand()
                            }
                        }
                        isDynamicIslandExpand = it
                    },
                    state = dynamicIslandState,
                    dynamicIslandStack = dynamicIslandStackStack,
                    onLongClick = onLongClick
                )
            }

            Column {
                ToolbarMain(
                    getSearchText = {
                        searchText.value = it
                    },
                    isSearchInputOpen = {
                        isSearchInputOpen = it
                    }
                )
                Column {
                    LazyColumn {
//                    item {
//                        NavBlockMain()
//                    }
                        item {
                            Box(modifier = modifier.padding(16.dp)) {
                                Text(
                                    text = context.getString(R.string.my_musics),
                                    fontSize = 18.sp,
                                    fontFamily = QuattrocentoSansFonts,
                                    fontWeight = FontWeight.Bold,
                                    style = TextStyle()
                                )
                            }
                        }

                        items(
                            items = audioList.filter {
                                it.title.contains(searchText.value, ignoreCase = true) ||
                                        it.author.contains(searchText.value, ignoreCase = true)
                            },
                            key = {
                                it.id
                            }
                        ) { music ->
                            Box(
                                modifier = modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                MusicItem(
                                    music = music,
                                    onClick = {
                                        dynamicIslandState =
                                            if (!isSearchInputOpen) {
                                                dynamicIslandStackStack =
                                                    StateDynamicIslandStack.Expand
                                                StateDynamicIsland.Expand()
                                            } else {
                                                dynamicIslandStackStack =
                                                    StateDynamicIslandStack.SearchInputOpen
                                                StateDynamicIsland.SearchInputOpen()
                                            }
                                        onItemClick.invoke(music)
                                    },
                                )
                            }

                        }

                    }
                }
            }


        }


    }


}


private val Start: Alignment.Horizontal = BiasAlignment.Horizontal(-1f)
private val CenterHorizontally: Alignment.Horizontal = BiasAlignment.Horizontal(0f)
private val End: Alignment.Horizontal = BiasAlignment.Horizontal(1f)

@Composable
private fun animateHorizontalAlignmentAsState(
    targetBiasValue: Float
): State<BiasAlignment.Horizontal> {
    val bias by animateFloatAsState(targetBiasValue)
    return derivedStateOf { BiasAlignment.Horizontal(bias) }
}

@Composable
fun animateAlignmentAsState(
    targetAlignment: Alignment,
): State<Alignment> {
    val biased = targetAlignment as BiasAlignment
    val horizontal by animateFloatAsState(
        biased.horizontalBias,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = 100f)
    )
    val vertical by animateFloatAsState(
        biased.verticalBias,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = 100f)
    )
    return derivedStateOf { BiasAlignment(horizontal, vertical) }
}