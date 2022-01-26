package net.teamof.whisper.components


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.teamof.whisper.R
import net.teamof.whisper.models.Message
import net.teamof.whisper.ui.theme.fontFamily

@ExperimentalAnimationApi
@Composable
fun Message(
    currentUserId: Long,
    data: Message,
    selection: Boolean,
    enableSelectionMode: () -> Unit
) {

    val isOwnMessage = data.user_id == currentUserId
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
                    onTap = {
                        messageSelected.value = !messageSelected.value
                    }
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
            Box {
                Text(
                    text = data.content,
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
                            if (isOwnMessage) MaterialTheme.colors.primary else Color(
                                0xfff7f8f7
                            )
                        )
                        .padding(vertical = 10.dp, horizontal = 15.dp)
                )
            }
        }
    }
}