package com.example.setter.ui.screen.main.viewModel


import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import azizjon.ibragimov.setter.data.local.music.Music
import azizjon.ibragimov.setter.data.repository.SetterRepository
import com.example.setter.media.exoplayer.MediaPlayerServiceConnection
import com.example.setter.media.exoplayer.currentPosition
import com.example.setter.media.exoplayer.isPlaying
import azizjon.ibragimov.setter.media.service.MediaPlayerService
import com.example.setter.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SetterViewModel @Inject constructor(
    private val repository: SetterRepository,
    serviceConnection: MediaPlayerServiceConnection
) : ViewModel() {
    var audioList = mutableStateListOf<Music>()
    val currentPlayingAudio = serviceConnection.currentPlayingAudio
    private val isConnected = serviceConnection.isConnected
    lateinit var rootMediaId: String
    var currentPlayBackPosition by mutableStateOf(0L)
    private var updatePosition = true
    val playbackState = serviceConnection.plaBackState
    val isAudioPlaying = mutableStateOf(false)/*: Boolean
        get() = playbackState.value?.isPlaying == true*/



    private val subscriptionCallback = object
        : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>
        ) {
            super.onChildrenLoaded(parentId, children)
        }
    }
    private val serviceConnection = serviceConnection.also {
        updatePlayBack()
    }

    fun getAudioPlayingState(): MutableState<Boolean> {
        isAudioPlaying.value = playbackState.value?.isPlaying == true
        return isAudioPlaying
    }

    private val currentDuration: Long
        get() = MediaPlayerService.currentDuration

    var currentAudioProgress = mutableStateOf(0f)

    init {
        viewModelScope.launch {
            audioList += getAndFormatAudioData()
            isConnected.collect {
                if (it) {
                    rootMediaId = serviceConnection.rootMediaId
                    serviceConnection.plaBackState.value?.apply {
                        currentPlayBackPosition = position
                    }
                    serviceConnection.subscribe(rootMediaId, subscriptionCallback)

                }
                Log.e("TAG", "isConnected: $it", )
            }
        }
    }

    private suspend fun getAndFormatAudioData(): List<Music> {
        return repository.getAudioData().map {
            val displayName = it.name.substringBefore(".")
            val artist = if (it.name.contains("<unknown>"))
                "Unknown Artist" else it.author
            it.copy(
                name = displayName,
                author = artist
            )
        }
    }



    fun playAudio(currentAudio: Music) {
        serviceConnection.playAudio(audioList)


        if (currentAudio.id == currentPlayingAudio.value?.id) {
            playbackState.value?.isPlaying()?.onEach {
                isAudioPlaying.value = it
                if (it) {
                    serviceConnection.transportControl.pause()
                } else {
                    serviceConnection.transportControl.play()
                }
            }?.launchIn(viewModelScope)
        } else {
            serviceConnection.transportControl
                .playFromMediaId(
                    currentAudio.id.toString(),
                    null
                )
        }


    }


    fun stopPlayBack() {
        serviceConnection.transportControl.stop()
    }

    fun fastForward() {
        serviceConnection.fastForward()
    }

    fun rewind() {
        serviceConnection.rewind()
    }

    fun skipToNext() {
        serviceConnection.skipToNext()
    }

    fun skipToPrevious() {
        serviceConnection.skipToPrevious()
    }

    fun shuffleMode(shuffleModeValue: Int = PlaybackStateCompat.SHUFFLE_MODE_NONE) {
        serviceConnection.shuffleMode(shuffleModeValue)
    }

    fun repeatMode(repeatModelValue: Int = PlaybackStateCompat.REPEAT_MODE_ALL) {
        serviceConnection.repeatMode(repeatModelValue)
    }

    fun seekTo(value: Float) {
        serviceConnection.transportControl.seekTo(
            (currentDuration * value / 100f).toLong()
        )
    }

    private fun updatePlayBack() {
        viewModelScope.launch {
            val position = playbackState.value?.currentPosition ?: 0

            if (currentPlayBackPosition != position) {
                currentPlayBackPosition = position
            }

            if (currentDuration > 0) {
                currentAudioProgress.value = (
                        currentPlayBackPosition.toFloat()
                                / currentDuration.toFloat() * 100f

                        )
            }

            delay(Constants.PLAYBACK_UPDATE_INTERVAL)
            if (updatePosition) {
                updatePlayBack()
            }


        }


    }

    override fun onCleared() {
        super.onCleared()
        serviceConnection.unSubscribe(
            Constants.MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {}
        )
        updatePosition = false

    }


}
