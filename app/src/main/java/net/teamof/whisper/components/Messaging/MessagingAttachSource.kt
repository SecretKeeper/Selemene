package net.teamof.whisper.components.Messaging

import android.net.Uri
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import net.teamof.whisper.R
import net.teamof.whisper.viewModel.Audio
import net.teamof.whisper.viewModel.StorageAudiosViewModel
import net.teamof.whisper.viewModel.StorageImagesViewModel

data class GalleryImage(
    val contentUri: Uri
)


@ExperimentalFoundationApi
@Composable
fun MessagingAttachSource(
    storageImagesViewModel: StorageImagesViewModel = viewModel(),
    storageAudiosViewModel: StorageAudiosViewModel = viewModel(),
) {

    val images: List<GalleryImage> by storageImagesViewModel.images.observeAsState(listOf())
    val audios: List<Audio> by storageAudiosViewModel.audios.observeAsState(listOf())
    val tabState = remember { mutableStateOf(0) }
    val titles = listOf(
        R.drawable.ic_image,
        R.drawable.ic_musical_notes,
        R.drawable.ic_document,
        R.drawable.ic_map_pin,
        R.drawable.ic_user
    )

    Column {

        Row(verticalAlignment = Alignment.CenterVertically) {
            titles.forEachIndexed { index, vector ->
                IconButton(onClick = { tabState.value = index }, modifier = Modifier.weight(1f)) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = vector),
                        contentDescription = null,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                }
            }
        }

        when(tabState.value) {
            0 -> {
                LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 130.dp)) {
                    itemsIndexed(images) { _, image ->
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
            1 -> {
                LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 130.dp)) {
                    itemsIndexed(audios) { _, audio ->
                        Text(text = audio.name)
                    }
                }
            }
        }

    }

}