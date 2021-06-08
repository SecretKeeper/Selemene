package net.teamof.whisper.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.MutableState


class AudioPlayer {

    companion object {
        val mediaPlayer = MediaPlayer()
        var playingStatus: MutableState<Boolean>? = null
        var currentContentUri: Uri? = null
    }

    fun play(context: Context, contentUris: Uri, updatePlayingStatus: MutableState<Boolean>) {

        if (playingStatus != null) playingStatus!!.value = false

        playingStatus = updatePlayingStatus

        // Check if new source want to play
        if (currentContentUri != contentUris) {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(context, contentUris)
            currentContentUri = contentUris
            mediaPlayer.setOnPreparedListener {
                it.start()
                playingStatus!!.value = true
            }
            mediaPlayer.setOnCompletionListener {
                playingStatus!!.value = false
            }
            mediaPlayer.prepareAsync()
        }


        if (currentContentUri == contentUris) {
            mediaPlayer.start()
            playingStatus!!.value = true
        }
    }

    fun pause() {
        mediaPlayer.pause()
        if (playingStatus != null) playingStatus!!.value = false
    }

}