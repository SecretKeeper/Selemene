package net.teamof.whisper.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import net.teamof.whisper.R
import net.teamof.whisper.models.Feed

@Composable
fun ImageFeed(
    data: Feed
) {

    val actionIconSize = 20

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
                        .weight(2f)
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
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_more_vertical),
                        contentDescription = null,
                        Modifier
                            .width(20.dp)
                            .height(20.dp),
                    )
                }
            }
            Image(
                painter = rememberGlidePainter(
                    request = data.image,
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(275.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                Row (Modifier.weight(1f)){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_heart),
                            contentDescription = null,
                            Modifier
                                .width(actionIconSize.dp)
                                .height(actionIconSize.dp)
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_share),
                            contentDescription = null,
                            Modifier
                                .width(actionIconSize.dp)
                                .height(actionIconSize.dp)
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_star),
                            contentDescription = null,
                            Modifier
                                .width(actionIconSize.dp)
                                .height(actionIconSize.dp)
                        )
                    }
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        Modifier
                            .width(actionIconSize.dp)
                            .height(actionIconSize.dp)
                    )
                }
            }
            Text(
                text = "Apply for feature following the link in our portfolio and we will publish your photos in our account @travel",
                fontSize = 13.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(top = 10.dp, bottom = 15.dp, start = 10.dp, end = 10.dp)
            )
            Divider(Modifier.padding(horizontal = 15.dp, vertical = 10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Comments")
                }
                Text(
                    text = "Sep 17th, 2021",
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}