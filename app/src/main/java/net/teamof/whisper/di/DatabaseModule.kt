package net.teamof.whisper.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.teamof.whisper.data.*
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

	@Provides
	fun provideConversationDAO(appDatabase: AppDatabase): ConversationDAO {
		return appDatabase.conversationDao()
	}

	@Provides
	fun provideMessageDAO(appDatabase: AppDatabase): MessageDAO {
		return appDatabase.messageDao()
	}

	@Provides
	fun provideProfileDAO(appDatabase: AppDatabase): ProfileDAO {
		return appDatabase.profileDao()
	}

	@Provides
	fun provideUserCountersDAO(appDatabase: AppDatabase): UserCountersDAO {
		return appDatabase.userCountersDAO()
	}
}