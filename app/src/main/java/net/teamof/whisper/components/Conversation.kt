package net.teamof.whisper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.ui.theme.fontFamily

@ExperimentalMaterialApi
@Composable
fun MessagePortal(
    data: Conversation,
    navController: NavController
) {
    Card(
        onClick = { navController.navigate("Messaging/${data.user_id}") },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
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
                    .width(60.dp)
                    .height(60.dp)
            )
            Column(
                Modifier
                    .weight(2f)
                    .padding(start = 15.dp)
            ) {
                Text(
                    text = data.username,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = data.last_message,
                    fontSize = 13.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colors.onSecondary
                )
            }
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 10.dp)) {
                Text(
                    text = data.last_message_time,
                    fontSize = 12.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
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