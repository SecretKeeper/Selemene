package net.teamof.whisper.components.Messaging

import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import net.teamof.whisper.R

data class GalleryImage(
    val contentUri: Uri
)

data class Audio(
    val name: String,
    val contentUri: Uri
)

@ExperimentalFoundationApi
@Composable
fun MessagingAttachSource() {

    val galleryImages = rememberSaveable { mutableListOf<GalleryImage>() }
    val audios = rememberSaveable { mutableListOf<Audio>() }
    val tabState = remember { mutableStateOf(0) }
    val titles = listOf("TAB 1", "TAB 2", "TAB 3 WITH LOTS OF TEXT")


    val context = LocalContext.current
    val collection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

    val audioCollection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }

    val projection = arrayOf(MediaStore.Images.Media._ID)
    val audioProjection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.DISPLAY_NAME
    )


    context.contentResolver.query(
        audioCollection, audioProjection, null, null, null, null
    )?.use { cursor ->
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
            audios += Audio(name, contentUri)
        }
    }

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

    Column {

        TabRow(selectedTabIndex = tabState.value) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = tabState.value == index,
                    onClick = { tabState.value = index }
                ) {
                    Text(text = title)
                }
            }
        }
        
        Text(text = tabState.value.toString())
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_image),
                    contentDescription = null,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_musical_notes),
                    contentDescription = null,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_document),
                    contentDescription = null,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_map_pin),
                    contentDescription = null,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_user),
                    contentDescription = null,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }
        }

        LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 130.dp)) {
            itemsIndexed(audios) { _, audio ->
                Text(text = audio.name)
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

}