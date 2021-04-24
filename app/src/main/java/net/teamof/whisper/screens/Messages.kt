package net.teamof.whisper.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Messages() {
    GlideImage(
        imageModel = "https://openthread.google.cn/images/ot-contrib-google.png",
        // Crop, Fit, Inside, FillHeight, FillWidth, None
        contentScale = ContentScale.Crop,
    )
}