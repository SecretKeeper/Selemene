package net.teamof.whisper

import android.app.Application

class Whisper : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}