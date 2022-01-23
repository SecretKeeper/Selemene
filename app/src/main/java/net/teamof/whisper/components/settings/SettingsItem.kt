package net.teamof.whisper.components.settings

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.teamof.whisper.R
import net.teamof.whisper.ui.theme.fontFamily

@Composable
fun SettingsItem(
    title: String,
    subTitle: String? = null,
    event: (() -> Unit)? = null,
    titleColor: Color? = Color.Black
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (event != null) {
                            event()
                        }
                    }
                )
            }) {
        Text(
            text = title,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp,
            color = titleColor ?: Color.Black,
            modifier = Modifier.weight(1f)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (subTitle != null) {
                Text(
                    text = subTitle,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_left),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp)
                    .rotate(180f)
            )
        }

    }
}