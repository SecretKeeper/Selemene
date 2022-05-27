package net.teamof.whisper.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.teamof.whisper.data.AppDatabase
import net.teamof.whisper.data.KeyValueDAO
import net.teamof.whisper.data.UserDAO
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

	@Singleton
	@Provides
	fun provideAppDatabase(application: Application): AppDatabase {
		return AppDatabase.getInstance(application)
	}

	@Provides
	fun provideUserDAO(appDatabase: AppDatabase): UserDAO {
		return appDatabase.userDAO()
	}

	@Provides
	fun provideKeyValueDAO(appDatabase: AppDatabase): KeyValueDAO {
		return appDatabase.keyValueDao()
	}
}