package net.teamof.whisper

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.objectbox.android.AndroidObjectBrowser
import timber.log.Timber

@HiltAndroidApp
class Whisper : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            val started = AndroidObjectBrowser(ObjectBox.store).start(this)
            Timber.i("Started: $started")
        }
    }
}