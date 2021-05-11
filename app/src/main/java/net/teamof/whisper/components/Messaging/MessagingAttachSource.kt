package net.teamof.whisper.components.Messaging

import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter

data class GalleryImage(
    val contentUri: Uri
)

@ExperimentalFoundationApi
    @Composable
    fun MessagingAttachSource() {

    val galleryImages =  rememberSaveable{ mutableListOf<GalleryImage>()}


    val context = LocalContext.current
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(MediaStore.Images.Media._ID)

        context.contentResolver.query(
            collection, projection, null, null, null, null
        )?.use { cursor ->
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



        LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 130.dp)) {
              itemsIndexed(galleryImages) { _, image ->
                  Image(
                      painter = rememberGlidePainter(request = image.contentUri, requestBuilder = {
                          apply(RequestOptions().centerCrop())
                      }),
                      contentDescription = null,
                      modifier = Modifier
                          .width(130.dp)
                          .height(130.dp)
                  )
              }
          }
    }

