package net.teamof.whisper.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import net.teamof.whisper.dataStore

class UserViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    
    val userId = context.dataStore.data.map { preferences
        ->
        preferences[longPreferencesKey("user_id")]
    }.asLiveData(viewModelScope.coroutineContext)

    suspend fun setUserId(userId: Long) {
        context.dataStore.edit { preferences ->
            preferences[longPreferencesKey("user_id")] = userId
        }
    }

}