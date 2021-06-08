package net.teamof.whisper.components.messaging

import android.media.MediaMetadataRetriever
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.teamof.whisper.R
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.utils.AudioPlayer
import net.teamof.whisper.viewModel.Audio

@ExperimentalFoundationApi
@Composable
fun BottomSheetAudiosTab(audios: List<Audio>) {

    val context = LocalContext.current

    LazyColumn {
        itemsIndexed(audios) { _, audioCtx ->

            val audio = AudioPlayer()
            val audioMetaData = MediaMetadataRetriever()
            audioMetaData.setDataSource(context, audioCtx.contentUri)
            val audioDuration =
                audioMetaData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!
                    .toInt()
            val isPlaying = remember { mutableStateOf(false) }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                IconButton(onClick = {
                    if (isPlaying.value) {
                        audio.stop()
                    } else {
                        audio.play(
                            context,
                            audioCtx.contentUri,
                            updatePlayingStatus = isPlaying
                        )
                    }
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = if (isPlaying.value) R.drawable.ic_add else R.drawable.ic_document),
                        contentDescription = null,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = audioCtx.name,
                        fontFamily = fontFamily,
                        fontSize = 16.sp
                    )
                }
                Text(
                    text = String.format(
                        "%02d:%02d",
                        ((audioDuration / 1000) / 60) % 60,
                        (audioDuration / 1000) % 60,
                    ),
                    fontFamily = fontFamily,
                    fontSize = 12.sp
                )
            }
        }
    }
}