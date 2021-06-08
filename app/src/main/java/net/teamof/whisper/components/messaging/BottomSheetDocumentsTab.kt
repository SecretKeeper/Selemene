package net.teamof.whisper.components.messaging

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import net.teamof.whisper.viewModel.Document

@ExperimentalFoundationApi
@Composable
fun BottomSheetDocumentsTab(documents: List<Document>) {
    LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 130.dp)) {
        itemsIndexed(documents) { _, document ->
            Text(text = document.name + document.type)
        }
    }
}