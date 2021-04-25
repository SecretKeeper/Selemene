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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import net.teamof.whisper.models.Contact

@Composable
fun Contact(data: Contact) {
    Card(elevation = 0.dp, modifier = Modifier.fillMaxWidth().clickable {  }) {
        Row(
            verticalAlignment  = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            Image(
                painter = rememberGlidePainter(request = data.image , fadeIn= true,
                    requestBuilder = {
                        apply(RequestOptions().circleCrop())
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
            )
            Column(Modifier.weight(2f).padding(start = 15.dp)) {
                Text(
                    text = data.username,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 7.dp)
                )
                Text(text = data.status, fontSize = 13.sp)
            }
        }
    }
}


@Preview
@Composable
fun ContactPreview() {
    Contact(Contact(
        1,
        "Jaina",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        "No Battrey",
        true
    ))
}