package net.teamof.whisper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import net.teamof.whisper.R
import net.teamof.whisper.models.Contact

@Composable
fun Contact(data: Contact, action: String) {

    var checked by rememberSaveable(data.selected) { mutableStateOf(data.selected) }

    Card(elevation = 0.dp, modifier = Modifier
        .fillMaxWidth()
        .padding(start = 40.dp)
        .clickable { checked = !checked }) {
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
                    .padding(start = 15.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = data.username,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(bottom = 7.dp)
                            .weight(1f)
                    )
                    if (action == "CreateGroup")
                        if (checked) Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_checkmark),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .padding(end = 10.dp)
                        )
                }
                Text(text = data.status, fontSize = 13.sp)
            }
        }
    }
}