package net.teamof.whisper.screens

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun SelfProfile() {

    val displayMetrics = Resources.getSystem().displayMetrics
    val heightDp = displayMetrics.heightPixels / displayMetrics.density

    Column {
        Image(
            painter = rememberImagePainter(data = "https://uupload.ir/files/2fk9_6087f98a3cd34_(2).jpg"),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(((heightDp * 59) / 100).dp)
        )
    }
}