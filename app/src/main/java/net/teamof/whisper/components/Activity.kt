package net.teamof.whisper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import net.teamof.whisper.models.Activity


@Composable
fun Activity(data: Activity) {
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
                .width(50.dp)
                .height(50.dp)
        )
        Column(
            Modifier
                .weight(2f)
                .padding(start = 20.dp, top= 10.dp)
        ) {
            Following()
            Text(
                text = "2 month ago - activities",
                fontSize = 13.sp,
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
        lineHeight = 22.sp
    )
}
