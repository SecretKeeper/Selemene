package net.teamof.whisper.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.teamof.whisper.utils.RealmKeyStoreUtils
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RealmKeyStore {
	@Singleton
	@Provides
	fun provideRealmKeyStore(application: Application) = RealmKeyStoreUtils(application)
}