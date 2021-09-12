package net.teamof.whisper.di

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("settings")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val settingsDataStore = context.dataStore

    fun getUserId(): Flow<Long> {

        val id = settingsDataStore.data.map { preferences
            ->
            preferences[longPreferencesKey("user_id")] ?: 0
        }

        return id
    }

    suspend fun setUserId(userId: Long) {
        settingsDataStore.edit { datastore ->
            datastore[longPreferencesKey("user_id")] = userId
        }
    }

}