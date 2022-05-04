package net.teamof.whisper.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.teamof.whisper.api.UsersAPI
import net.teamof.whisper.repositories.ConversationRepository
import net.teamof.whisper.repositories.KeyValueRepository
import net.teamof.whisper.repositories.MessageRepository
import net.teamof.whisper.repositories.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Repositories {
	@Singleton
	@Provides
	fun provideUserRepository() = UserRepository()

	@Singleton
	@Provides
	fun provideMessageRepository() = MessageRepository()

	@Singleton
	@Provides
	fun provideKeyValueRepository() = KeyValueRepository()

	@Singleton
	@Provides
	fun provideConversationRepository(usersAPI: UsersAPI) = ConversationRepository(usersAPI)
}