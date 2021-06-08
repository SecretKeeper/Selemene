package net.teamof.whisper.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.MutableState


class AudioPlayer {

    companion object {
        val mediaPlayer = MediaPlayer()
        var playingStatus: MutableState<Boolean>? = null
    }

    fun play(context: Context, contentUris: Uri, updatePlayingStatus: MutableState<Boolean>) {
        if (playingStatus != null) playingStatus!!.value = false
        playingStatus = updatePlayingStatus

        mediaPlayer.reset()
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.setDataSource(context, contentUris)
            mediaPlayer.setOnPreparedListener {
                it.start()
                playingStatus!!.value = true
            }
            mediaPlayer.setOnCompletionListener {
                playingStatus!!.value = false
            }
            mediaPlayer.prepareAsync()
        }
    }

    fun stop() {
        mediaPlayer.stop()
        if (playingStatus != null) playingStatus!!.value = false
    }

}