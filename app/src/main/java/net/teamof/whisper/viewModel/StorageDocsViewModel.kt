package net.teamof.whisper.viewModel

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class Document(
    val name: String,
    val type: String,
    val contentUri: Uri
)

@RequiresApi(Build.VERSION_CODES.Q)
class StorageDocsViewModel(application: Application) : AndroidViewModel(application) {

    @RequiresApi(Build.VERSION_CODES.Q)
    private val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Files.getContentUri(
            MediaStore.VOLUME_EXTERNAL
        )
    } else {
        MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
    }

    private val projection = arrayOf(
        MediaStore.Files.FileColumns._ID,
        MediaStore.Files.FileColumns.TITLE,
        MediaStore.Files.FileColumns.SIZE,
        MediaStore.Files.FileColumns.MIME_TYPE,
    )

    private val where =
        MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?" + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"

    private val args = arrayOf(
        MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf"),
        MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc"),
        MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx"),
        MimeTypeMap.getSingleton().getMimeTypeFromExtension("txt"),
        MimeTypeMap.getSingleton().getMimeTypeFromExtension("xls"),
        MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx"),
        MimeTypeMap.getSingleton().getMimeTypeFromExtension("html")
    )

    private val _audios: MutableLiveData<List<Document>> by lazy {
        MutableLiveData<List<Document>>(loadDocs(application))
    }

    val docs: LiveData<List<Document>> get() = _audios

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun loadDocs(application: Application): List<Document> {

        val storageDocs: MutableList<Document> = mutableListOf()

        application.contentResolver.query(collection, projection, where, args, null, null)
            ?.use { cursor ->
                val idColumn = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE)
                val typeColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)

                while (cursor.moveToNext()) {
                    val audioId = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val type = cursor.getString(typeColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                        audioId
                    )
                    storageDocs += Document(name , type ,contentUri)
                }
            }

        return storageDocs

    }

}