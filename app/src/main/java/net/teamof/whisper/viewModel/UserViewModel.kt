package net.teamof.whisper.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import net.teamof.whisper.di.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {
    fun getUserID(): LiveData<Long> {
        return dataStoreManager.getUserId().asLiveData()
    }

    suspend fun setUserID(userID: Long) {
        dataStoreManager.setUserId(userID)
    }

}