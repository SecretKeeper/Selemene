package net.teamof.whisper.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import net.teamof.whisper.components.MessagePortal
import net.teamof.whisper.models.MessagePortal
import net.teamof.whisper.viewModel.MessagePortalsViewModel

@Composable
fun Messages(
    navController: NavController,
    messages: List<MessagePortal>
) {
    LazyColumn {
        itemsIndexed(messages) { _, item ->
            MessagePortal(item, navController)
        }
    }
}