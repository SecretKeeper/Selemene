package net.teamof.whisper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import net.teamof.whisper.models.MessagePortal

@Composable
fun MessagePortal(
    data: MessagePortal
) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable { }) {
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
                    .width(60.dp)
                    .height(60.dp)
            )
            Column(
                Modifier
                    .weight(2f)
                    .padding(start = 15.dp)) {
                Text(
                    text = data.username,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 7.dp)
                )
                Text(text = data.last_message, fontSize = 13.sp)
            }
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 10.dp)) {
                Text(
                    text = data.last_message_time,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 7.dp)
                )
                if (data.unread_messages != 0) Text(
                    text = data.unread_messages.toString(),
                    fontSize = 13.sp
                )
            }
        }
    }
}