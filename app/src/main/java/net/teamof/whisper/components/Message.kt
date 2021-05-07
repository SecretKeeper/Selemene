package net.teamof.whisper.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.teamof.whisper.models.Message

@Composable
fun Message(data: Message) {

    val isOwnMessage = data.user_id == 1


    Row(
        horizontalArrangement = if (isOwnMessage) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {



        Text(
            text = data.content,
            color = if (isOwnMessage) Color.White else Color.Black,
            modifier = Modifier.padding(vertical = 15.dp)
                .background(if(isOwnMessage) MaterialTheme.colors.primary else Color.LightGray)
                .padding(vertical = 5.dp, horizontal = 10.dp)
        )
    }

}