package net.teamof.whisper.components.messaging


import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
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
import net.teamof.whisper.R
import net.teamof.whisper.viewModel.*

data class GalleryImage(
    val contentUri: Uri
)


@ExperimentalFoundationApi
@Composable
fun MessagingAttachSource(
    storageImagesViewModel: StorageImagesViewModel = viewModel(),
    storageAudiosViewModel: StorageAudiosViewModel = viewModel(),
    storageDocsViewModel: StorageDocsViewModel = viewModel()
) {
    val images: List<GalleryImage> by storageImagesViewModel.images.observeAsState(listOf())
    val audios: List<Audio> by storageAudiosViewModel.audios.observeAsState(listOf())
    val documents: List<Document> by storageDocsViewModel.docs.observeAsState(listOf())
    val tabState = remember { mutableStateOf(0) }
    val titles = listOf(
        R.drawable.ic_image,
        R.drawable.ic_musical_notes,
        R.drawable.ic_document,
        R.drawable.ic_map_pin
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

        when (tabState.value) {
            0 -> BottomSheetGalleryTab(images)
            1 -> BottomSheetAudiosTab(audios)
            2 -> BottomSheetDocumentsTab(documents)
            3 -> Text(text = "Tab 3")
        }

    }

}