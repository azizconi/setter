package azizjon.ibragimov.setter

import android.Manifest
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import aziz.ibragimov.setter.ui.screen.main.HomeScreen
import com.example.setter.ui.screen.main.viewModel.SetterViewModel
import com.example.setter.ui.theme.StatusBarTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val permissionState = rememberPermissionState(
                permission = Manifest.permission.READ_EXTERNAL_STORAGE
            )

            StatusBarTheme()
            val lifecycleOwner = LocalLifecycleOwner.current

            DisposableEffect(key1 = lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_RESUME) {
                        permissionState.launchPermissionRequest()
                    }

                }
                lifecycleOwner.lifecycle.addObserver(observer)


                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }

            }


            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                if (permissionState.hasPermission) {
                    val setterViewModel = viewModel(
                        modelClass = SetterViewModel::class.java
                    )


                    val audioList = setterViewModel.audioList
                    HomeScreen(
                        progress = setterViewModel.currentAudioProgress.value,
                        onProgressChange = {
                            setterViewModel.seekTo(it)
                        },
                        audioList = audioList,
                        currentPlayingAudio = setterViewModel.currentPlayingAudio.value,
                        onStart = {
                            setterViewModel.playAudio(it)
                        },
                        isAudioPlaying = setterViewModel.isAudioPlaying.value,
                        onItemClick = {
                            setterViewModel.playAudio(it)
                        },
                        onNext = {
                            setterViewModel.skipToNext()
                        },
                        onPrevious = {
                            setterViewModel.skipToPrevious()
                        },
                        onShuffled = {
                            setterViewModel.shuffleMode(
                                if (it) PlaybackStateCompat.SHUFFLE_MODE_ALL else PlaybackStateCompat.SHUFFLE_MODE_NONE
                            )
                        },
                        onRepeat = {
                            setterViewModel.repeatMode(
                                if (it) PlaybackStateCompat.REPEAT_MODE_ONE else PlaybackStateCompat.REPEAT_MODE_ALL
                            )
                        }
                    )

                    LaunchedEffect(setterViewModel.getAudioPlayingState().value) {
                        Log.e("TAG", "onCreate: ${setterViewModel.getAudioPlayingState().value}")
                    }


                    Log.e(
                        "TAG",
                        "onCreate: currentPlayingAudio ${setterViewModel.currentPlayingAudio}",
                    )

                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                    }
                }
            }


        }


    }


}