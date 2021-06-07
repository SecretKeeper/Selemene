package net.teamof.whisper.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri


class AudioPlayer {

    companion object {
        val mediaPlayer = MediaPlayer()
    }

    fun play(context: Context,contentUris: Uri) {
        mediaPlayer.reset()

        if(!mediaPlayer.isPlaying) {
            mediaPlayer.setDataSource(context, contentUris)
            mediaPlayer.setOnPreparedListener {
                it.start()
            }
            mediaPlayer.prepareAsync()
        }
    }

    fun stop() {
        mediaPlayer.stop()
    }

}