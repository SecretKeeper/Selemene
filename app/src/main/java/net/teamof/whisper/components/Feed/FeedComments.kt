package net.teamof.whisper.components.Feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import net.teamof.whisper.models.Comment

@Composable
fun FeedComments(comments: List<Comment>) {
    comments.forEach { comment ->
        Comment(comment)
    }
}

@Composable
fun Comment(
    data: Comment
) {
    Card(
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
        ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            Image(
                painter = rememberGlidePainter(request = data.user_image,
                    requestBuilder = {
                        apply(RequestOptions().circleCrop())
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
            )
            Column(
                Modifier
                    .weight(2f)
                    .padding(start = 15.dp)) {
                Row(Modifier.padding(bottom = 7.dp)) {
                    Text(
                        text = data.username,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                    )
                    Text(
                        text = data.time,
                        fontSize = 13.sp,
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier.padding(start = 7.dp)
                    )
                }
                Text(text = data.content, fontSize = 13.sp, lineHeight = 18.sp)
            }
        }
    }
}