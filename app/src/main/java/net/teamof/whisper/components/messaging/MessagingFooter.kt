package net.teamof.whisper.components.messaging

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import net.teamof.whisper.R
import net.teamof.whisper.models.Message
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.MessagesViewModel

@ExperimentalPermissionsApi
@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun MessagingFooter(
    bottomSheetState: ModalBottomSheetState,
    messagesViewModel: MessagesViewModel,
    currentUserId: Long,
    toUserID: Long
) {
    val scope = rememberCoroutineScope()
    val text = remember { mutableStateOf("") }

    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    Column(Modifier.background(Color(red = 245, green = 245, blue = 253))) {
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
                        when {
                            cameraPermissionState.hasPermission -> scope.launch { bottomSheetState.show() }

                            cameraPermissionState.shouldShowRationale ||
                                    !cameraPermissionState.permissionRequested -> cameraPermissionState.launchPermissionRequest()
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
                value = text.value,
                onValueChange = { text.value = it },
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
                if (text.value.isNotEmpty()) {

                    val message = Message(
                        user_id = currentUserId,
                        to_user_id = toUserID,
                        content = text.value,
                    )

                    messagesViewModel.sendMessage(message)

                    text.value = ""
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