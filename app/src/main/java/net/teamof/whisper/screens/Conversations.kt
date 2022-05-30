package net.teamof.whisper.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import net.teamof.whisper.components.Conversation
import net.teamof.whisper.data.Conversation
import net.teamof.whisper.ui.theme.BottomNavigationHeight
import net.teamof.whisper.viewModel.ConversationActionsViewModel
import net.teamof.whisper.viewModel.ConversationsViewModel
import net.teamof.whisper.viewModel.ProfileViewModel

@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Conversations(
	navController: NavController,
	conversationsViewModel: ConversationsViewModel,
	conversationActionsViewModel: ConversationActionsViewModel,
	profileViewModel: ProfileViewModel
) {

	val conversations: List<Conversation> by conversationsViewModel.conversations.observeAsState(
		listOf()
	)

	Column(
		Modifier
			.padding(bottom = BottomNavigationHeight)
			.verticalScroll(rememberScrollState())
	) {
		conversations.forEachIndexed { _, conversation ->
			Conversation(
				conversation,
				navController,
				conversationActionsViewModel,
				profileViewModel
			)
		}
	}
}
