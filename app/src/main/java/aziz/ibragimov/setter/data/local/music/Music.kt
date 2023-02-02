package aziz.ibragimov.setter.data.local.music

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Music(
    val id: Long,
    val uri: Uri,
    val name: String,
    val duration: Int,
    val size: Int,
    val albumId: Long,
    var albumartUri: Uri,
    val title: String,
    val author: String,
    val uriFile: Uri,

    val image: ByteArray? = null
) : Parcelable