package net.teamof.whisper.viewModel

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class Audio(
    val name: String,
    val contentUri: Uri
)

class StorageAudiosViewModel(application: Application): AndroidViewModel(application) {

    private val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Audio.Media.getContentUri(
            MediaStore.VOLUME_EXTERNAL
        )
    } else {
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    }

    private val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.DISPLAY_NAME
    )

    private val _audios: MutableLiveData<List<Audio>> by lazy {
        MutableLiveData<List<Audio>>(loadImages(application))
    }

    val audios: LiveData<List<Audio>> get() = _audios

    private fun loadImages(application: Application): List<Audio> {

        val storageAudios: MutableList<Audio> = mutableListOf()

        application.contentResolver.query(collection, projection, null, null, null, null)
        ?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val nameColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val audioId = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    audioId
                )
                storageAudios += Audio(name ,contentUri)
            }
        }

        return storageAudios

    }

    fun setPlaying(value: Boolean) {

    }

}