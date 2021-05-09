package net.teamof.whisper.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    data: Message,
    selection: Boolean,
    enableSelectionMode: () -> Unit
) {

    val isOwnMessage = data.user_id == 1
    val messageSelected = remember { mutableStateOf(data.selected) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { enableSelectionMode() },
                    onTap = { messageSelected.value = !messageSelected.value }
                )
            }
    ) {
        AnimatedVisibility(visible = selection) {
            Column(
                modifier = Modifier
                    .width(23.dp)
                    .height(23.dp)
            ) {
                AnimatedVisibility(
                    visible = messageSelected.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.ic_checkmark
                        ),
                        tint = MaterialTheme.colors.primary,
                        contentDescription = null,
                        modifier = Modifier
                            .width(23.dp)
                            .height(23.dp)
                    )
                }
                AnimatedVisibility(
                    visible = !messageSelected.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.ic_circle
                        ),
                        tint = Color.DarkGray,
                        contentDescription = null,
                        modifier = Modifier
                            .width(23.dp)
                            .height(23.dp)
                    )
                }
            }
        }
        Row(
            horizontalArrangement = if (isOwnMessage) Arrangement.End else Arrangement.Start,
            modifier = Modifier
                .padding(start = 15.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = data.content,
                color = if (isOwnMessage) Color.White else Color.Black,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .background(if (isOwnMessage) MaterialTheme.colors.primary else Color.LightGray)
                    .padding(vertical = 5.dp, horizontal = 10.dp)
            )
        }
    }
}