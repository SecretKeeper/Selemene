package net.teamof.whisper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import net.teamof.whisper.R
import net.teamof.whisper.models.Feed
import net.teamof.whisper.models.MessagePortal

@Composable
fun MessagePortal(
    data: Feed
) {

    val actionIconSize = 25

    Card(Modifier.fillMaxWidth().padding(bottom = 25.dp), elevation = 0.dp) {
        Column {
            Row(
                verticalAlignment  = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                Image(
                    painter = rememberGlidePainter(request = data.image ,
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
                        .padding(start = 15.dp)) {
                    Text(
                        text = data.username,
                        fontWeight = FontWeight.SemiBold ,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    data.location?.let { Text(text = it, fontSize = 12.sp) }
                }
                Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 10.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_more_vertical ),
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
            Row(Modifier.padding(vertical = 15.dp, horizontal = 10.dp)) {
                Row(
                    Modifier
                        .weight(0.25f)
                        .clickable { },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_heart ),
                        contentDescription = null,
                        Modifier
                            .width(actionIconSize.dp)
                            .height(actionIconSize.dp)
                    )
                    Text(text = "Like", Modifier.padding(start = 10.dp))
                }
                Row(
                    Modifier.weight(0.3f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_message ),
                        contentDescription = null,
                        Modifier
                            .width(actionIconSize.dp)
                            .height(actionIconSize.dp)
                    )
                    Text(text = "Like", Modifier.padding(start = 10.dp))
                }
                Row(
                    Modifier.weight(0.3f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_share ),
                        contentDescription = null,
                        Modifier
                            .width(actionIconSize.dp)
                            .height(actionIconSize.dp),
                    )
                    Text(text = "Like", Modifier.padding(start = 10.dp))
                }
                Row(
                    Modifier.weight(0.25f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star ),
                        contentDescription = null,
                        Modifier
                            .width(actionIconSize.dp)
                            .height(actionIconSize.dp)
                    )
                    Text(text = "Like", Modifier.padding(start = 10.dp))
                }
            }
            Divider(Modifier.padding(horizontal = 15.dp, vertical = 10.dp))
            Text(
                text = "Apply for feature following the link in our portfolio and we will publish your photos in our account @travel",
                fontSize = 13.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}