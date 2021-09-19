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
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import net.teamof.whisper.R
import net.teamof.whisper.components.feed.FeedActions
import net.teamof.whisper.components.feed.FeedComments
import net.teamof.whisper.models.Feed
import net.teamof.whisper.ui.theme.fontFamily

@OptIn(ExperimentalCoilApi::class)
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
                    painter = rememberImagePainter(data = data.image,
                        builder = {
                            transformations(CircleCropTransformation())
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
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    data.location?.let {
                        Text(
                            text = it,
                            fontSize = 12.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }
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
                                Text(
                                    "Clear History",
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(start = 15.dp, end = 10.dp)
                                )
                            }
                        }
                        DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_user),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(22.dp)
                                        .height(22.dp)
                                )
                                Text(
                                    "Add Contact",
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(start = 15.dp, end = 10.dp)
                                )
                            }
                        }
                        DropdownMenuItem(onClick = { /* Handle settings! */ }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_blocked),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp)
                                )
                                Text(
                                    "Block",
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(start = 15.dp, end = 10.dp)
                                )
                            }
                        }
                    }
                }
            }
            data.image?.let {
                Image(
                    painter = rememberImagePainter(
                        data = data.image,
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(275.dp)
                )
            }
            Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)) {
                Text(
                    text = "Apply for feature following the link in our portfolio and we will publish your photos in our account @travel",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 15.dp, bottom = 20.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Tags...",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Sep 17th , 2021",
                        fontSize = 12.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colors.onSecondary
                    )
                }
                Divider(Modifier.padding(vertical = 10.dp))
                FeedActions(
                    showCommentsState = showComments.value,
                    showComments = { showComments.value = true },
                    hideComments = { showComments.value = false },
                    numberOfComments = data.comments.size
                )

                if (showComments.value)
                    FeedComments(comments = data.comments)

            }
        }
    }
}