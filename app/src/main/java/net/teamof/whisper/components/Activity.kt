package net.teamof.whisper.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import net.teamof.whisper.models.Activity
import net.teamof.whisper.ui.theme.fontFamily


@OptIn(ExperimentalCoilApi::class)
@Composable
fun Activity(data: Activity) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 10.dp)
	) {
		AsyncImage(
			model = data.image,
			contentDescription = null,
			contentScale = ContentScale.Crop,
			modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(CircleShape)
		)
		Column(
            Modifier
                .weight(2f)
                .padding(start = 20.dp, top = 10.dp)
		) {
			Following()
			Text(
				text = "2 month ago - activities",
				fontSize = 13.sp,
				fontFamily = fontFamily,
				fontWeight = FontWeight.Normal,
				color = Color.DarkGray,
				modifier = Modifier.padding(top = 15.dp)
			)
		}
	}
}

@Composable
fun Following() {
	Text(
		text = "Sylvanas Windrunner starts following you. you want also follow him back?",
		fontSize = 14.sp,
		fontFamily = fontFamily,
		fontWeight = FontWeight.Normal,
		lineHeight = 22.sp
	)
}
