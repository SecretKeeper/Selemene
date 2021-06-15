package net.teamof.whisper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.rememberCoilPainter
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.MessagesViewModel
import org.ocpsoft.prettytime.PrettyTime

@ExperimentalMaterialApi
@Composable
fun Conversation(
    conversation: Conversation,
    navController: NavController
) {

    val cachedConversation = remember(conversation) { mutableStateOf(conversation) }
    val messagesViewModel = MessagesViewModel(conversation.channel)
    val lastMessage = messagesViewModel.getLastMessage()

    Card(
        onClick = { navController.navigate("Messaging/${cachedConversation.value.channel}") },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            Image(
                painter = rememberCoilPainter(request = cachedConversation.value.user_image,
                    requestBuilder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
            )
            Column(
                Modifier
                    .weight(2f)
                    .padding(start = 15.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = cachedConversation.value.username,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 10.dp)
                    )
                    if (lastMessage != null) {
                        Text(
                            text = PrettyTime().format(lastMessage.created_at),
                            fontSize = 12.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(bottom = 7.dp)
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = lastMessage?.content ?: "There is no message",
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