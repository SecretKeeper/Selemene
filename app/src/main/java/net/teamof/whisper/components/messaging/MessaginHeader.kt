package net.teamof.whisper.components.messaging

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.DelicateCoroutinesApi
import net.teamof.whisper.R
import net.teamof.whisper.components.conversation.Avatar
import net.teamof.whisper.data.UserWithProfileAndCounters
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.ProfileViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@DelicateCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MessagingHeader(
	navController: NavController,
	profileViewModel: ProfileViewModel,
	to_user_id: Long,
	selection: MutableState<Boolean>
) {
	val user = profileViewModel.userState.observeAsState()

	Column {

		AnimatedContent(
			targetState = selection.value,
			transitionSpec = {
				if (selection.value) {
					slideInVertically(initialOffsetY = { height -> height }) + fadeIn() with
							slideOutVertically(targetOffsetY = { height -> -height }) + fadeOut()
				} else {
					slideInVertically(initialOffsetY = { height -> -height }) + fadeIn() with
							slideOutVertically(targetOffsetY = { height -> -height }) + fadeOut()
				}.using(
					SizeTransform(clip = true)
				)
			}
		) { showActions ->
			if (showActions)
				MessagingActions(selection)
			else
				MainHeader(navController, profileViewModel, user.value!!)
		}
	}
}


@Composable
fun MainHeader(
	navController: NavController,
	profileViewModel: ProfileViewModel,
	user: UserWithProfileAndCounters
) {

	val interactionSource = remember { MutableInteractionSource() }
	val expanded = remember { mutableStateOf(false) }

	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp)
	) {
		IconButton(onClick = { navController.navigate("Conversations") }) {
			Icon(
				painter = painterResource(id = R.drawable.ic_chevron_left),
				contentDescription = null,
				Modifier
					.width(22.dp)
					.height(22.dp)
			)
		}
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.weight(1f)
				.padding(horizontal = 15.dp)
				.clickable(
					interactionSource = interactionSource,
					indication = null
				) {
					profileViewModel.getUserWithProfileByIdBeforeNavigate(
						user.user.userId,
					) { navController.navigate("Profile") }
				}
		) {
			Avatar(
				user_image = user.user.avatar!!,
				username = user.user.username!!,
				width = 50,
				height = 50
			)
			Column(
				Modifier
					.padding(start = 15.dp)
			) {
				Text(
					text = user.user.username!!,
					fontFamily = fontFamily,
					fontWeight = FontWeight.Bold,
					fontSize = 16.sp,
					modifier = Modifier.padding(bottom = 5.dp)
				)

				Text(
					text = user.profile.profile.status!!,
					fontSize = 13.sp,
					fontFamily = fontFamily,
					fontWeight = FontWeight.Normal,
					color = MaterialTheme.colors.onSecondary,
					overflow = TextOverflow.Ellipsis,
					maxLines = 1
				)
			}
		}
		Box {
			IconButton(onClick = { expanded.value = true }) {
				Icon(
					painter = painterResource(id = R.drawable.ic_more_vertical),
					contentDescription = null,
					Modifier
						.width(23.dp)
						.height(23.dp)
				)
			}
			DropdownMenu(
				expanded = expanded.value,
				onDismissRequest = { expanded.value = false }
			) {
				DropdownMenuItem(onClick = {

				}) {
					Text("Refresh")
				}
				DropdownMenuItem(onClick = { /* Handle settings! */ }) {
					Text("Settings")
				}
				Divider()
				DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
					Text("Send Feedback")
				}
			}
		}
	}
}


@Composable
fun MessagingActions(selection: MutableState<Boolean>) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp)
	) {
		IconButton(onClick = {
			selection.value = false
		}) {
			Icon(
				painter = painterResource(id = R.drawable.ic_x),
				contentDescription = null,
				Modifier
					.width(25.dp)
					.height(25.dp)
			)
		}
		Row(Modifier.weight(1f), Arrangement.End, Alignment.CenterVertically) {
			IconButton(onClick = { }) {
				Icon(
					painter = painterResource(id = R.drawable.ic_forward),
					contentDescription = null,
					Modifier
						.width(22.dp)
						.height(22.dp)
				)
			}
			IconButton(onClick = { }) {
				Icon(
					painter = painterResource(id = R.drawable.ic_copy),
					contentDescription = null,
					Modifier
						.width(20.dp)
						.height(20.dp)
				)
			}
			IconButton(onClick = { }) {
				Icon(
					painter = painterResource(id = R.drawable.ic_trash),
					contentDescription = null,
					Modifier
						.width(23.dp)
						.height(23.dp)
				)
			}
		}
	}
}