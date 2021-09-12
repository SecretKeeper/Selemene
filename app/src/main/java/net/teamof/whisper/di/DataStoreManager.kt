package net.teamof.whisper.di

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("settings")

class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val settingsDataStore = context.dataStore

    fun getUserId(): Flow<Long> {

        val id = settingsDataStore.data.map { preferences
            ->
            preferences[longPreferencesKey("user_id")] ?: 0
        }

        Timber.d("FROM VIEWMODEL $id")

        return id
    }

    suspend fun setUserId(userId: Long) {
        Timber.d("F from Datacenters: $userId")
        settingsDataStore.edit { datastore ->
            datastore[longPreferencesKey("user_id")] = userId
        }
    }

}