package net.teamof.whisper.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import net.teamof.whisper.components.MessagePortal
import net.teamof.whisper.models.MessagePortal

@ExperimentalMaterialApi
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