package net.teamof.whisper

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import io.objectbox.android.AndroidObjectBrowser
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class Whisper : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            val started = AndroidObjectBrowser(ObjectBox.store).start(this)
            Timber.i("Started: $started")
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {

        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.INFO)
            .build()
    }
}