package net.teamof.whisper.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class AudioPlayer(context: Context, contentUri: Uri) {

    private val mediaPlayer: MediaPlayer = MediaPlayer.create(context, contentUri)

    val duration get() = mediaPlayer.duration

    fun play() { mediaPlayer.start() }

    fun stop() { mediaPlayer.stop() }

}