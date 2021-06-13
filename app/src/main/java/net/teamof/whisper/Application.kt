package net.teamof.whisper

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Whisper : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}