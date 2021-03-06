package net.teamof.whisper.components.messaging

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage

@OptIn(ExperimentalCoilApi::class)
@ExperimentalFoundationApi
@Composable
fun BottomSheetGalleryTab(images: List<GalleryImage>) {
	LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 130.dp)) {
		itemsIndexed(images) { _, image ->
			AsyncImage(
				model = image.contentUri,
				contentScale = ContentScale.FillBounds,
				contentDescription = null,
				modifier = Modifier
					.width(130.dp)
					.height(130.dp)
			)
		}
	}
}