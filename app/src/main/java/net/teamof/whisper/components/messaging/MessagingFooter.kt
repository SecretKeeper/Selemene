package net.teamof.whisper.components.messaging

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import net.teamof.whisper.R
import net.teamof.whisper.data.Message
import net.teamof.whisper.screens.LocalEditMessage
import net.teamof.whisper.screens.LocalInputText
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.MessagesViewModel

@ExperimentalPermissionsApi
@SuppressLint("SimpleDateFormat")
@ExperimentalMaterialApi
@Composable
fun MessagingFooter(
    bottomSheetState: ModalBottomSheetState,
    messagesViewModel: MessagesViewModel,
    currentUserId: Long,
    toUserID: Long
) {
    val scope = rememberCoroutineScope()
    val inputTextCtx = LocalInputText.current

    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    Column(Modifier.background(Color(red = 245, green = 245, blue = 253))) {
        EditingPreview()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                start = 15.dp,
                end = 20.dp,
                top = 3.dp,
                bottom = 3.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .clip(shape = CircleShape)
                    .background(MaterialTheme.colors.primary)
                    .clickable {
                        when (cameraPermissionState.status) {
                            is PermissionStatus.Granted -> scope.launch { bottomSheetState.show() }

                            is PermissionStatus.Denied -> cameraPermissionState.launchPermissionRequest()
                        }
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .width(23.dp)
                        .height(23.dp)
                        .align(Alignment.Center)
                )
            }
            OutlinedTextField(
                value = inputTextCtx.value,
                onValueChange = { inputTextCtx.value = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                textStyle = TextStyle(fontSize = 14.sp),
                placeholder = {
                    Text(
                        text = "Write message here...",
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            IconButton(onClick = {
                if (inputTextCtx.value.isNotEmpty()) {

                    val message = Message(
                        sender = currentUserId,
                        receiver = toUserID,
                        content = inputTextCtx.value,
                    )

                    messagesViewModel.sendMessage(message)

                    inputTextCtx.value = ""
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = null,
                    Modifier
                        .width(23.dp)
                        .height(23.dp)
                )
            }
        }
    }
}


@Composable
fun EditingPreview() {
    val currentEditMessageCtx = LocalEditMessage.current
    val inputTextCtx = LocalInputText.current

    AnimatedVisibility(visible = (currentEditMessageCtx.value.id != 0L)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 15.dp, end = 5.dp)
        ) {
            Text(text = currentEditMessageCtx.value.content, modifier = Modifier.weight(1f))
            IconButton(onClick = {
                currentEditMessageCtx.value = Message()
                inputTextCtx.value = ""
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_x),
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier
                        .width(22.dp)
                        .height(22.dp)
                )
            }
        }
    }
}