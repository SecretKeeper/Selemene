package net.teamof.whisper.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import net.teamof.whisper.utils.RealmKeyStoreUtils
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RealmModule {
	@Provides
	@Singleton
	fun providesRealm(
		@ApplicationContext context: Context,
		realmKeyStore: RealmKeyStoreUtils
	): Realm {
		Realm.init(context)
		val realmConfiguration = RealmConfiguration
			.Builder()
			.name("RealmValkyrie")
			.encryptionKey(realmKeyStore.getOrCreateEncryptionKey())
			.deleteRealmIfMigrationNeeded()
			.build()
		Realm.setDefaultConfiguration(realmConfiguration)
		return Realm.getDefaultInstance()
	}
}