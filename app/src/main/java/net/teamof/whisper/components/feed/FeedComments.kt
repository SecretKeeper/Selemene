package net.teamof.whisper.components.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import net.teamof.whisper.models.Comment
import net.teamof.whisper.ui.theme.fontFamily

@Composable
fun FeedComments(comments: List<Comment>) {
	comments.forEach { comment ->
		Comment(comment)
	}
}

@OptIn(ExperimentalCoilApi::class)
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
			AsyncImage(
				model = data.user_image,
				contentDescription = null,
				contentScale = ContentScale.Crop,
				modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape)
			)
			Column(
                Modifier
                    .weight(2f)
                    .padding(start = 15.dp)
			) {
				Row(Modifier.padding(bottom = 7.dp)) {
					Text(
						text = data.username,
						fontFamily = fontFamily,
						fontWeight = FontWeight.SemiBold,
						fontSize = 14.sp,
					)
					Text(
						text = data.time,
						fontSize = 13.sp,
						fontFamily = fontFamily,
						fontWeight = FontWeight.Normal,
						color = MaterialTheme.colors.onSecondary,
						modifier = Modifier.padding(start = 7.dp)
					)
				}
				Text(
					text = data.content,
					fontSize = 13.sp,
					lineHeight = 18.sp,
					fontFamily = fontFamily,
					fontWeight = FontWeight.Normal,
				)
			}
		}
	}
}