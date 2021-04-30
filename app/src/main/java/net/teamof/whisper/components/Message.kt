package net.teamof.whisper.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.teamof.whisper.models.Message

@Composable
fun Message(data: Message) {
    Box(modifier = Modifier.padding(vertical = 15.dp)
        .clip(shape = RoundedCornerShape(20.dp))
        .background(MaterialTheme.colors.primary)

        .padding(vertical = 5.dp, horizontal = 10.dp)
        ) {
        Text(text = data.content, color = Color.White)
    }
}