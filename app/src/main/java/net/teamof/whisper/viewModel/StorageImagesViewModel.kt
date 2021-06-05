package net.teamof.whisper.viewModel

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.teamof.whisper.components.Messaging.GalleryImage

class StorageImagesViewModel(application: Application): AndroidViewModel(application) {

    private val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(
            MediaStore.VOLUME_EXTERNAL
        )
    } else {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    private val projection = arrayOf(MediaStore.Images.Media._ID)

    private val _images: MutableLiveData<List<GalleryImage>> by lazy {
        MutableLiveData<List<GalleryImage>>(loadImages(application))
    }

    val images: LiveData<List<GalleryImage>> get() = _images

    private fun loadImages(application: Application): List<GalleryImage> {

        val galleryImages: MutableList<GalleryImage> = mutableListOf()

        application.contentResolver.query(collection, projection, null, null, null, null)
        ?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val imageId = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    imageId
                )
                galleryImages += GalleryImage(contentUri)
            }
        }

        return galleryImages

    }

}