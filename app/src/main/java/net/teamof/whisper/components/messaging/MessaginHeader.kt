package net.teamof.whisper.components.messaging

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import net.teamof.whisper.R
import net.teamof.whisper.ui.theme.fontFamily

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MessagingHeader(
    navController: NavController,
    selection: MutableState<Boolean>,
    username: String
) {

    val interactionSource = remember { MutableInteractionSource() }
    val expanded = remember { mutableStateOf(false) }


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
}