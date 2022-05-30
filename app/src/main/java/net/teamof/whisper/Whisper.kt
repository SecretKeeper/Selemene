package net.teamof.whisper

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Whisper : Application(), Configuration.Provider {

	@Inject
	lateinit var workerFactory: HiltWorkerFactory

	override fun onCreate() {
		super.onCreate()
	}

	override fun getWorkManagerConfiguration(): Configuration {

		return Configuration.Builder()
			.setWorkerFactory(workerFactory)
			.setMinimumLoggingLevel(Log.INFO)
			.build()
	}
}