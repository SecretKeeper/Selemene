package net.teamof.whisper.components

import BackPressHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import net.teamof.whisper.R
import net.teamof.whisper.components.conversation.Avatar
import net.teamof.whisper.data.Conversation
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.ConversationActionsViewModel
import net.teamof.whisper.viewModel.ProfileViewModel
import org.ocpsoft.prettytime.PrettyTime

@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Conversation(
	conversation: Conversation,
	navController: NavController,
	conversationActionsViewModel: ConversationActionsViewModel,
	profileViewModel: ProfileViewModel
) {

	val cachedConversation = remember(conversation) { mutableStateOf(conversation) }
	val selectedConversationsState: List<Long> by conversationActionsViewModel.selectedConversations.observeAsState(
		listOf()
	)
	val showConversationActions: Boolean by conversationActionsViewModel.showActionsState.observeAsState(
		false
	)

	val selectIconScaleState =
		animateFloatAsState(if (cachedConversation.value.target_user in selectedConversationsState) 1f else 0.0f)

	if (showConversationActions) {
		BackPressHandler {
			conversationActionsViewModel.hideActions()
		}
	}


	Card(
		shape = RoundedCornerShape(0.dp),
		modifier = Modifier
			.pointerInput(Unit) {
				detectTapGestures(
					onTap = {
						if (!showConversationActions) {
							profileViewModel.getUserWithProfileByIdBeforeNavigate(cachedConversation.value.target_user) {
								navController.navigate("Messaging/${cachedConversation.value.target_user}")
							}
						} else
							if (cachedConversation.value.target_user in selectedConversationsState)
								conversationActionsViewModel.unselectConversationByToUserID(
									cachedConversation.value.target_user
								)
							else
								conversationActionsViewModel.selectConversationByToUserID(
									cachedConversation.value.target_user
								)
					},
					onLongPress = {
						conversationActionsViewModel.showActions(cachedConversation.value.target_user)
					}
				)
			}
			.fillMaxWidth()
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 10.dp, horizontal = 12.dp)
		) {
			Box {
				Avatar(cachedConversation.value.avatar ?: "", cachedConversation.value.username)

				Icon(
					imageVector = ImageVector.vectorResource(id = R.drawable.ic_checkmark_conversation),
					tint = Color.Unspecified,
					contentDescription = null,
					modifier = Modifier
						.width(22.dp)
						.scale(selectIconScaleState.value)
				)
			}

			Column(
				Modifier
					.weight(2f)
					.padding(start = 15.dp)
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically,
				) {
					Text(
						text = cachedConversation.value.username,
						fontFamily = fontFamily,
						fontWeight = FontWeight.Bold,
						fontSize = 16.sp,
						modifier = Modifier
							.weight(1f)
							.padding(bottom = 5.dp)
					)
					Text(
						text = PrettyTime().format(conversation.last_message_time),
						fontSize = 13.sp,
						fontFamily = fontFamily,
						fontWeight = FontWeight.Normal,
						modifier = Modifier.padding(bottom = 5.dp)
					)
				}
				Row(verticalAlignment = Alignment.CenterVertically) {
					Text(
						text = conversation.last_message,
						fontSize = 13.sp,
						fontFamily = fontFamily,
						fontWeight = FontWeight.Normal,
						color = MaterialTheme.colors.onSecondary,
						lineHeight = 18.sp,
						maxLines = 1,
						overflow = TextOverflow.Ellipsis,
						modifier = Modifier.weight(1f)
					)
					Text(
						text = if (cachedConversation.value.unread_messages > 0) cachedConversation.value.unread_messages.toString() else "",
						fontSize = 13.sp
					)
				}
			}
		}
	}
}