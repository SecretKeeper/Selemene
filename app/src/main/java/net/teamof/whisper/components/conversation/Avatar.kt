package net.teamof.whisper.components.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

@Composable
fun Avatar(user_image: String, username: String) {
    if (user_image != "")
        Image(
            painter = rememberImagePainter(data = user_image,
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
        )
    else
        Box(
            Modifier
                .width(58.dp)
                .height(58.dp)
                .clip(shape = CircleShape)
                .background(MaterialTheme.colors.primary)
        ) {
            Text(
                text = username[0].toString(),
                color = Color.White,
                fontSize = 32.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-1).dp)
            )
        }
}