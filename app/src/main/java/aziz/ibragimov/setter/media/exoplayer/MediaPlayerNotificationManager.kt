package aziz.ibragimov.setter.media.exoplayer

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import aziz.ibragimov.setter.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.setter.utils.Constants
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

internal class MediaPlayerNotificationManager(
    private val context: Context,
    sessionToken: MediaSessionCompat.Token,
    notificationListener: PlayerNotificationManager.NotificationListener
) {
    private val notificationManager: PlayerNotificationManager

    init {
        val mediaController = MediaControllerCompat(context, sessionToken)


        val builder = PlayerNotificationManager.Builder(
            context,
            Constants.PLAYBACK_NOTIFICATION_ID,
            Constants.PLAYBACK_NOTIFICATION_CHANNEL_ID
        )


        with(builder) {
            setMediaDescriptionAdapter(DescriptionAdapter(mediaController))
            setNotificationListener(notificationListener)
            setChannelNameResourceId(R.string.notification_channel)
            setChannelDescriptionResourceId(R.string.notification_channel_description)
        }

        notificationManager = builder.build()


        with(notificationManager) {
            setMediaSessionToken(sessionToken)
            setSmallIcon(R.drawable.music)
            setUseRewindAction(false)
            setUseFastForwardAction(false)
        }


    }


    fun hideNotification() {
        notificationManager.setPlayer(null)
    }

    fun showNotification(player: Player) {
        notificationManager.setPlayer(player)
    }


    inner class
    DescriptionAdapter(private val controller: MediaControllerCompat) :
        PlayerNotificationManager.MediaDescriptionAdapter {

        override fun getCurrentContentTitle(player: Player): CharSequence =
            controller.metadata.description.title.toString()

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return controller.sessionActivity
        }


        override fun getCurrentContentText(player: Player): CharSequence? =
            controller.metadata.description.subtitle

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {

            Log.e("TAG", "getCurrentLargeIcon: ${controller.metadata.description.iconUri}", )
//            loadBitmap(
//                controller.metadata.description.iconUri?.path
//                    ?: "android.resource://${context.packageName}/${R.drawable.music}",
//                callback
//            )
            return controller.metadata.description.iconBitmap
        }


        
    }


}