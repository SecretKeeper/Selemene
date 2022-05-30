package net.teamof.whisper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.teamof.whisper.utils.DATABASE_NAME

/**
 * The Room database for this app
 */
@Database(
	entities = [
		User::class,
		KeyValue::class,
		Conversation::class,
		Message::class,
		Profile::class,
		UserCounters::class
	],
	version = 1,
	exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
	abstract fun userDAO(): UserDAO

	abstract fun keyValueDao(): KeyValueDAO

	abstract fun conversationDao(): ConversationDAO

	abstract fun messageDao(): MessageDAO

	abstract fun profileDao(): ProfileDAO

	abstract fun userCountersDAO(): UserCountersDAO

	companion object {

		// For Singleton instantiation
		@Volatile
		private var instance: AppDatabase? = null

		fun getInstance(context: Context): AppDatabase {
			return instance ?: synchronized(this) {
				instance ?: buildDatabase(context).also { instance = it }
			}
		}

		private fun buildDatabase(context: Context): AppDatabase {
			return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
				.build()
		}
	}
}