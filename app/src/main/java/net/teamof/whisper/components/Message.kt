package net.teamof.whisper.components


import android.content.res.Resources
import androidx.compose.animation.Animatable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.teamof.whisper.R
import net.teamof.whisper.data.Message
import net.teamof.whisper.screens.LocalEditMessage
import net.teamof.whisper.screens.LocalInputText
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.MessagesViewModel

@ExperimentalAnimationApi
@Composable
fun Message(
    currentUserId: Long,
    data: Message,
    selection: Boolean,
    messagesViewModel: MessagesViewModel,
    enableSelectionMode: () -> Unit
) {
    val isOwnMessage = data.sender == currentUserId
    val expandedMessageDropdown = remember { mutableStateOf(false) }
    val messageSelected = remember { mutableStateOf(false) }
    val selectIconScaleState =
        animateFloatAsState(if (selection && messageSelected.value) 1f else if (selection && !messageSelected.value) 0.25f else 0.0f)

    Box(
        modifier = Modifier
            .padding(start = 10.dp, end = 15.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { enableSelectionMode() },
                    onTap = { expandedMessageDropdown.value = true }
                )
            }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_checkmark_conversation),
            tint = Color.Unspecified,
            contentDescription = null,
            modifier = Modifier
                .width(22.dp)
                .scale(selectIconScaleState.value)
        )
        Row(
            horizontalArrangement = if (isOwnMessage) Arrangement.End else Arrangement.Start,
            modifier = Modifier
                .padding(start = 15.dp)
                .fillMaxWidth()
        ) {

            MessageDropdown(data, messagesViewModel, isOwnMessage, expandedMessageDropdown)

            MessageContent(data, isOwnMessage)

        }
    }
}


@Composable
fun MessageContent(message: Message, isOwnMessage: Boolean) {

    val primaryColor = MaterialTheme.colors.primary
    val backgroundColor =
        remember { Animatable(if (message.id == 0L) Color(0x990a5dfe) else primaryColor) }

    LaunchedEffect(key1 = message.id) {
        if (message.id != 0L) backgroundColor.animateTo(primaryColor, animationSpec = tween(500))
    }

    Text(
        text = message.content,
        color = if (isOwnMessage) Color.White else Color.Black,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        modifier = Modifier
            .padding(vertical = 15.dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 25.dp,
                    topEnd = 25.dp,
                    bottomStart = if (isOwnMessage) 25.dp else 2.dp,
                    bottomEnd = if (isOwnMessage) 2.dp else 25.dp
                )
            )
            .background(
                if (isOwnMessage) backgroundColor.value else Color(
                    0xfff7f8f7
                )
            )
            .padding(vertical = 10.dp, horizontal = 15.dp)
    )
}


@Composable
fun MessageDropdown(
    message: Message,
    messagesViewModel: MessagesViewModel,
    isOwnMessage: Boolean,
    expandedMessageDropdown: MutableState<Boolean>
) {

    val currentEditMessageCtx = LocalEditMessage.current
    val inputTextCtx = LocalInputText.current

    val displayMetrics = Resources.getSystem().displayMetrics
    val widthDp = displayMetrics.widthPixels / displayMetrics.density


    if (expandedMessageDropdown.value)
        DropdownMenu(
            expanded = expandedMessageDropdown.value,
            onDismissRequest = { expandedMessageDropdown.value = false },
            offset = DpOffset(
                if (isOwnMessage) (widthDp - 150).dp else 0.dp,
                0.dp
            ),
        ) {
            if (isOwnMessage && message.id == 0L)
                DropdownMenuItem(onClick = {
                    expandedMessageDropdown.value = false
                }) {
                    messagesViewModel.sendAgain(message)
                    Text("Send Again")
                }
            if (isOwnMessage && message.id != 0L)
                DropdownMenuItem(onClick = {
                    currentEditMessageCtx.value = message
                    expandedMessageDropdown.value = false
                    inputTextCtx.value = message.content
                }) {
                    Text("Edit")
                }
            if (isOwnMessage && message.id == 0L)
                DropdownMenuItem(onClick = {
                    messagesViewModel.cancelSendingMessage(message.localId)
                    expandedMessageDropdown.value = false
                }) {
                    Text("Cancel")
                }
            else
                DropdownMenuItem(onClick = {
                    expandedMessageDropdown.value = false
                }) {
                    Text("Delete")
                }
        }

}