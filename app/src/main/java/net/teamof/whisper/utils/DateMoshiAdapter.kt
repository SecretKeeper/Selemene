package net.teamof.whisper.utils

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*

class DateMoshiAdapter : JsonAdapter<Date>() {

    companion object {
        const val SERVER_FORMAT = ("yyyy-MM-dd HH:mm:ss.SSS")
    }

    private val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            synchronized(dateFormat) {
                dateFormat.parse(dateAsString)
            }
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        synchronized(dateFormat) {
            writer.value(value?.toString())
        }
    }
}