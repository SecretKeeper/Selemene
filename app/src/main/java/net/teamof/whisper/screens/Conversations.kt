package net.teamof.whisper.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import net.teamof.whisper.components.Conversation
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.viewModel.ConversationsViewModel

@ExperimentalMaterialApi
@Composable
fun Conversations(
    navController: NavController,
    conversationsViewModel: ConversationsViewModel = viewModel(),
) {

    val conversations: List<Conversation> by conversationsViewModel.conversations.observeAsState(
        listOf()
    )

    Column(
        Modifier
            .padding(bottom = 70.dp)
            .verticalScroll(rememberScrollState())
    ) {
        conversations.forEachIndexed { _, conversation ->
            Conversation(conversation, navController)
        }
    }
}
