package com.example.setter.media.exoplayer

import android.os.SystemClock
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline val PlaybackStateCompat.isPlaying: Boolean
    get() = state == PlaybackStateCompat.STATE_BUFFERING ||
            state == PlaybackStateCompat.STATE_PLAYING


fun PlaybackStateCompat.isPlaying(): Flow<Boolean> = flow {
    emit(
        state == PlaybackStateCompat.STATE_BUFFERING ||
                state == PlaybackStateCompat.STATE_PLAYING
    )
}

inline val PlaybackStateCompat.currentPosition: Long
    get() = if (state == PlaybackStateCompat.STATE_PLAYING) {
        val timeDelta = SystemClock.elapsedRealtime() - lastPositionUpdateTime
        Log.e("TAG", "currentPosition():${(position + (timeDelta * playbackSpeed)).toLong()}: ", )
        (position + (timeDelta * playbackSpeed)).toLong()
    } else {
        position
    }



