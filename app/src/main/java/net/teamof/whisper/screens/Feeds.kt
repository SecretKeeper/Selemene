package net.teamof.whisper.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.teamof.whisper.components.Feed
import net.teamof.whisper.models.Feed
import net.teamof.whisper.viewModel.FeedsViewModel

@Composable
fun Feeds(feedsViewModel: FeedsViewModel) {

    val feeds: List<Feed> by feedsViewModel.feeds.observeAsState(listOf())

    LazyColumn(
        Modifier
            .background(Color(red = 245, green = 245, blue = 253))
            .padding(top = 70.dp, bottom = 70.dp, start = 15.dp, end = 15.dp)
    ) {
        itemsIndexed(feeds) { _, item ->
            Feed(item)
        }
    }
}