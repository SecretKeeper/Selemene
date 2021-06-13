package net.teamof.whisper

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import io.objectbox.android.AndroidObjectBrowser

@HiltAndroidApp
class Whisper : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
        
        if (BuildConfig.DEBUG) {
            val started = AndroidObjectBrowser(ObjectBox.store).start(this)
            Log.i("ObjectBrowser", "Started: $started")
        }
    }
}