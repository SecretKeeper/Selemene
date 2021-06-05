package net.teamof.whisper.screens

import BackPressHandler
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import kotlinx.coroutines.launch
import net.teamof.whisper.R
import net.teamof.whisper.components.Message
import net.teamof.whisper.components.Messaging.MessagingAttachSource
import net.teamof.whisper.models.Message
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.MessagesViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@SuppressLint("RememberReturnType")
@Composable
fun Messaging(
    navController: NavController,
    messagesViewModel: MessagesViewModel = viewModel(),
    username: String
) {

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) true else false
        }
    val context = LocalContext.current
    val messages: List<Message> by messagesViewModel.messages.observeAsState(listOf())
    val selection = remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }
    val text = remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    if (selection.value || bottomSheetState.isVisible) {
        BackPressHandler {
            selection.value = false
            scope.launch { bottomSheetState.hide() }
        }
    }

    ModalBottomSheetLayout(sheetContent = { MessagingAttachSource() } , sheetState = bottomSheetState) {

        Column() {
            Column(Modifier.height(75.dp)) {
                AnimatedVisibility(visible = !selection.value) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp)
                    ) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_chevron_left),
                                contentDescription = null,
                                Modifier
                                    .width(25.dp)
                                    .height(25.dp)
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
                                    navController.navigate("profile")
                                }
                        ) {
                            Image(
                                painter = rememberGlidePainter(request = "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
                                    requestBuilder = {
                                        apply(RequestOptions().circleCrop())
                                    }
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(50.dp)
                            )
                            Column(
                                Modifier
                                    .padding(start = 15.dp)
                            ) {
                                Text(
                                    text = username,
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(bottom = 5.dp)
                                )
                                Text(
                                    text = "Last seen recently",
                                    fontSize = 12.sp,
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colors.onSecondary,
                                    modifier = Modifier.padding(bottom = 5.dp)
                                )
                            }
                        }
                        Box() {
                            IconButton(onClick = { expanded.value = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_more_vertical),
                                    contentDescription = null,
                                    Modifier
                                        .width(25.dp)
                                        .height(25.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = expanded.value,
                                onDismissRequest = { expanded.value = false }
                            ) {
                                DropdownMenuItem(onClick = { /* Handle refresh! */ }) {
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
                AnimatedVisibility(
                    visible = selection.value,
                    enter = slideInVertically(initialOffsetY = { -40 })
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp)
                    ) {
                        IconButton(onClick = { selection.value = false }) {
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
            }
            Column(Modifier.weight(1f)) {
                LazyColumn(reverseLayout = true) {
                    itemsIndexed(messages) { _, message ->
                        Message(
                            message,
                            selection.value,
                            enableSelectionMode = { selection.value = true },
                        )
                    }
                }
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
                        onClick = { scope.launch {
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
                        } },
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
                    IconButton(onClick = { }) {
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
    }
}