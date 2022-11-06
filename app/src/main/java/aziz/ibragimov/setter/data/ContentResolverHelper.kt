package azizjon.ibragimov.setter.data

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import azizjon.ibragimov.setter.data.local.music.Music
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ContentResolverHelper @Inject constructor(
    @ApplicationContext val context: Context
) {


    @WorkerThread
    fun getAudioData(): List<Music> {
        return fetchSongs()
    }


    private fun fetchSongs(): MutableList<Music> {
        val songs = ArrayList<Music>()
        val songLibraryUri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
        )

        val sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC"


        context.contentResolver.query(songLibraryUri, projection, null, null, sortOrder).use { cursor ->

            //cache the cursor indices
            val idColumn = cursor!!.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)

            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val durationColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            val albumIdColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val musicNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)

            //getting the values
            while (cursor.moveToNext()) {
                //get values of columns for a give audio file
                val id = cursor.getLong(idColumn)
                var name = cursor.getString(nameColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getInt(sizeColumn)
                val albumId = cursor.getLong(albumIdColumn)

                val musicName = cursor.getString(musicNameColumn)
                val artist = cursor.getString(artistColumn)


                val uriFile =
                    ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                //song uri
                val uri =
                    ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)


                //album art uri
                val albumartUri = ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/albumart"),
                    albumId
                )

                //remove .mp3 extension on song's name
//                name = name.substring(0, name.lastIndexOf("."))

                //song item
                val song =
                    Music(
                        id,
                        uri,
                        name,
                        duration,
                        size,
                        albumId,
                        albumartUri,
                        musicName,
                        artist,
                        uriFile
                    )
                //add song to songs list

                if (size / 1000 >= 300) {
                    songs.add(song)
                }
            }
            return songs
        }

    }




}