package net.teamof.whisper.sharedprefrences

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import net.teamof.whisper.utils.ENCRYPTED_PREFERENCES_NAME
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesManagerImpl @Inject constructor(private val application: Application) :
	SharedPreferencesManager {

	private val encryptedPreferencesName = ENCRYPTED_PREFERENCES_NAME

	private var prefs: SharedPreferences

	init {
		prefs = initializeEncryptedSharedPreferencesManager()
	}

	private fun initializeEncryptedSharedPreferencesManager(): SharedPreferences {
		val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
		return EncryptedSharedPreferences.create(
			encryptedPreferencesName,
			masterKeyAlias,
			application,
			EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
			EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
		)
	}

	override fun <T : Any?> set(key: String, value: T) {
		with(prefs.edit()) {
			when (value) {
				is String? -> putString(key, value)
				is Int -> putInt(key, value.toInt())
				is Boolean -> putBoolean(key, value)
				is Float -> putFloat(key, value.toFloat())
				is Long -> putLong(key, value.toLong())
				else -> {
					Log.e("SharedPreferences", "Unsupported Type: $value")
				}
			}
			apply()
		}
	}

	override fun getString(key: String, defaultValue: String): String {
		val value = getValue(key, defaultValue)
		return value as String
	}

	override fun getInt(key: String, defaultValue: Int): Int {
		val value = getValue(key, defaultValue)
		return value as Int
	}

	override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
		val value = getValue(key, defaultValue)
		return value as Boolean
	}

	override fun getLong(key: String, defaultValue: Long): Long {
		val value = getValue(key, defaultValue)
		return value as Long
	}

	override fun getFloat(key: String, defaultValue: Float): Float {
		val value = getValue(key, defaultValue)
		return value as Float
	}

	private fun getValue(key: String, defaultValue: Any?): Any? {
		var value = prefs.all[key]
		value = value ?: defaultValue
		return value
	}

	override fun contains(key: String): Boolean {
		return prefs.contains(key)
	}

	override fun remove(key: String) {
		prefs.edit {
			remove(key)
		}
	}

	override fun clear() {
		prefs.edit {
			clear()
		}
	}
}