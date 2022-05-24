package net.teamof.whisper.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.teamof.whisper.api.*
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitAPI {
	@Singleton
	@Provides
	fun provideAuthAPI(retrofit: Retrofit): AuthAPI =
		retrofit.create(AuthAPI::class.java)

	@Singleton
	@Provides
	fun provideSearchAPI(retrofit: Retrofit): SearchAPI = retrofit.create(SearchAPI::class.java)

	@Singleton
	@Provides
	fun provideUsersAPI(retrofit: Retrofit): UsersAPI = retrofit.create(UsersAPI::class.java)

	@Singleton
	@Provides
	fun provideAccountAPI(retrofit: Retrofit): AccountAPI =
		retrofit.create(AccountAPI::class.java)

	@Singleton
	@Provides
	fun provideProfileAPI(retrofit: Retrofit): ProfileAPI = retrofit.create(ProfileAPI::class.java)

}