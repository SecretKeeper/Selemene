package net.teamof.whisper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys")
data class KeyValue(
	@PrimaryKey @ColumnInfo(name = "key") val key: String,
	@ColumnInfo(name = "value") val value: String?,
)