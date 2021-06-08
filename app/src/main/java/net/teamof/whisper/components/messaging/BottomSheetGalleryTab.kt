package net.teamof.whisper.components.messaging

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter

@ExperimentalFoundationApi
@Composable
fun BottomSheetGalleryTab(images: List<GalleryImage>) {
    LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 130.dp)) {
        itemsIndexed(images) { _, image ->
            Image(
                painter = rememberGlidePainter(
                    request = image.contentUri,
                    requestBuilder = {
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