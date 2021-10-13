package net.teamof.whisper.components.messaging

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch
import net.teamof.whisper.R
import net.teamof.whisper.models.Message
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.MessagesViewModel
import net.teamof.whisper.viewModel.UserViewModel
import java.util.*

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun MessagingFooter(
    bottomSheetState: ModalBottomSheetState,
    messagesViewModel: MessagesViewModel,
    userViewModel: UserViewModel,
    toUserID: Long
) {
    val currentUserId = userViewModel.getUserID().value ?: 0
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val text = remember { mutableStateOf("") }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) true else false
        }

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
            IconButton(
                onClick = {
                    scope.launch {
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) -> {
                                scope.launch { bottomSheetState.show() }
                            }
                            else -> {
                                launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }
                },
                Modifier
                    .width(27.dp)
                    .height(27.dp)
                    .clip(shape = CircleShape)
                    .background(MaterialTheme.colors.primary)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .width(23.dp)
                        .height(23.dp)
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
                        created_at = Date()
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