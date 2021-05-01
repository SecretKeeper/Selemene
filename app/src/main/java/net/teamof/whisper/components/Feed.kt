package net.teamof.whisper.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import net.teamof.whisper.R
import net.teamof.whisper.components.Feed.FeedActions
import net.teamof.whisper.models.Feed

@Composable
fun Feed(
    data: Feed
) {

    val expanded = remember { mutableStateOf(false) }
    val showComments = remember { mutableStateOf(false) }

    Card(
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp)
            .animateContentSize(),
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                Image(
                    painter = rememberGlidePainter(request = data.image,
                        requestBuilder = {
                            apply(RequestOptions().circleCrop())
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .width(45.dp)
                        .height(45.dp)
                )
                Column(
                    Modifier
                        .weight(1f)
                        .padding(start = 15.dp)
                ) {
                    Text(
                        text = data.username,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    data.location?.let { Text(text = it, fontSize = 12.sp) }
                }
                Box(
                    modifier = Modifier
                ) {
                    IconButton(onClick = { expanded.value = !expanded.value }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_more_vertical),
                            contentDescription = null,
                            modifier = Modifier
                                .width(22.dp)
                                .height(22.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        DropdownMenuItem(onClick = { /* Handle refresh! */ }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_eraser),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(25.dp)
                                        .height(25.dp)
                                )
                                Text("Clear History", modifier = Modifier.padding(start = 15.dp))
                            }
                        }
                        DropdownMenuItem(onClick = { /* Handle settings! */ }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_blocked),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(23.dp)
                                        .height(23.dp)
                                )
                                Text("Block", modifier = Modifier.padding(start = 15.dp))
                            }
                        }
                        DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_user),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(24.dp)
                                        .height(24.dp)
                                )
                                Text("Add Contact", modifier = Modifier.padding(start = 15.dp))
                            }
                        }
                    }
                }
            }
            data.image?.let {
                Image(
                    painter = rememberGlidePainter(
                        request = data.image,
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(275.dp)
                )
            }
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                Text(
                    text = "Apply for feature following the link in our portfolio and we will publish your photos in our account @travel",
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 15.dp, bottom = 20.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Tags...", fontSize = 14.sp, modifier = Modifier.weight(1f))
                    Text(
                        text = "Sep 17th , 2021",
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onSecondary
                    )
                }
                Divider(Modifier.padding(vertical = 10.dp))
                FeedActions(
                    showCommentsState = showComments.value,
                    showComments = { showComments.value = true },
                    hideComments = { showComments.value = false })
                if (showComments.value)
                    data.comments.forEach { comment ->
                        Text(text = comment.content)
                    }
            }
        }
    }
}