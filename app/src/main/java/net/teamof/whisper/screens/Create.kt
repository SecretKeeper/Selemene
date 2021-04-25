package net.teamof.whisper.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.teamof.whisper.R

@Composable
fun Create() {
    Column(
        Modifier
            .background(Color(red = 245, green = 245, blue = 253))
            .padding(20.dp)
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(Modifier.padding(40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = null,
                    Modifier
                        .width(70.dp)
                        .height(70.dp)
                )
                Text(
                    text = "Start chatting instantly with anybody using AES-256 encryption ",
                    Modifier.padding(top = 30.dp),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                )
                Divider(Modifier.padding(vertical = 20.dp))
            }
        }
    }
}