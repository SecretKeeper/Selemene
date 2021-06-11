package net.teamof.whisper.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.teamof.whisper.components.MessagePortal
import net.teamof.whisper.models.MessagePortal

@ExperimentalMaterialApi
@Composable
fun Messages(
    navController: NavController,
    messages: List<MessagePortal>
) {
    Column(
        Modifier
            .padding(bottom = 70.dp)
            .verticalScroll(rememberScrollState())
    ) {
        messages.forEachIndexed { _, message ->
            MessagePortal(message, navController)
        }
    }
}