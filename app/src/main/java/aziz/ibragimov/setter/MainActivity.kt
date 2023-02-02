package aziz.ibragimov.setter

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import aziz.ibragimov.setter.media.service.dynamic_island.DynamicIslandService
import aziz.ibragimov.setter.ui.screen.main.HomeScreen
import aziz.ibragimov.setter.ui.screen.main.viewModel.SetterViewModel
import aziz.ibragimov.setter.utils.SetOnDestroyListener
import com.example.setter.ui.theme.StatusBarTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.lang.Thread.sleep


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val REQUEST_CODE = 10101
    }

    lateinit var setterViewModel: SetterViewModel


    @OptIn(ExperimentalPermissionsApi::class)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setterViewModel = ViewModelProvider(this)[SetterViewModel::class.java]

//        startServiceThread = dynamicIslandStartService(setterViewModel, isRunService)

        setContent {


            val permissionState = rememberPermissionState(
                permission = Manifest.permission.READ_EXTERNAL_STORAGE
            )

            val lifecycleOwner = LocalLifecycleOwner.current


            StatusBarTheme()

            DisposableEffect(key1 = lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_RESUME) {
                        permissionState.launchPermissionRequest()
//                        widgetPermissionState.launchPermissionRequest()
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
//                    val setterViewModel = viewModel(
//                        modelClass = SetterViewModel::class.java
//                    )

//                    serviceViewModel = setterViewModel
                    SetOnDestroyListener(lifecycle = lifecycleOwner.lifecycle) {
                        setterViewModel.stopMode()
                    }


                    val audioList = setterViewModel.audioList

//                    val scope = rememberCoroutineScope()

//                    val thread = Thread {
//                        getThread(setterViewModel)
//                    }

//                    SetOnStopOrStartListener(
//                        lifecycle = lifecycle,
//                        onStart = {
//                            if (thread.isAlive) {
//                                isLive = false
//                                thread.interrupt()
//                            }
//                            Log.e("TAG", "onCreate thread.isAlive onStart: $isLive", )
//                        },
//                        onStop = {
//                            if (!thread.isAlive) {
//                                thread.start()
//                                isLive = true
//                            }
//                            Log.e("TAG", "onCreate thread.isAlive onStop: ${thread.isAlive}", )
//
//                        }
//                    )


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
                        },
                        onLongClick = {
//                            if (Settings.canDrawOverlays(this)) {
//
//                            } else {
                            checkDrawOverlayPermission()
//                                Log.e(TAG, "onCreate: ", )
//                            }
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

    override fun onStart() {
        super.onStart()

//        dynamicIslandStopService()
        Log.e("TAG", "onStart: ")
    }

    override fun onStop() {
        super.onStop()
        Log.e("TAG", "onStop: ")
//        startServiceThread.start()
//        dynamicIslandStartService(setterViewModel)
    }


    private fun checkDrawOverlayPermission() {

        // Checks if app already has permission to draw overlays
//        if (!Settings.canDrawOverlays(this)) {

        // If not, form up an Intent to launch the permission request
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse(
                "package:$packageName"
            )
        )

        // Launch Intent, with the supplied request code
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if a request code is received that matches that which we provided for the overlay draw request
        if (requestCode == REQUEST_CODE) {

            // Double-check that the user granted it, and didn't just dismiss the request
            if (Settings.canDrawOverlays(this)) {

                // Launch the service
//                launchMainService()
            } else {
                Toast.makeText(
                    this,
                    "Sorry. Can't draw overlays without permission...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun dynamicIslandStartService(
        setterViewModel: SetterViewModel,
        isRun: Boolean
    ) = CoroutineScope(Dispatchers.Main).launch {



        if (setterViewModel.currentPlayingAudio.value != null && setterViewModel.isAudioPlaying.value) {
            while (true) {
                Intent(
                    applicationContext,
                    DynamicIslandService::class.java
                ).also {
                    val currentMusic = setterViewModel.currentPlayingAudio.value
                    val currentAudioProgress =
                        setterViewModel.currentAudioProgress.value
                    val isAudioPlaying = setterViewModel.isAudioPlaying.value

                    it.putExtra("currentMusic", currentMusic)
                    it.putExtra("currentAudioProgress", currentAudioProgress)
                    it.putExtra("isAudioPlaying", isAudioPlaying)
                    startService(it)
                }
                delay(1000)
            }


        }


    }

    private fun dynamicIslandStopService() {
        Intent(
            applicationContext,
            DynamicIslandService::class.java
        ).also {
            stopService(it)
        }
    }

}