package net.teamof.whisper.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimeFormat {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun toISO8601UTC(date: Date): String {
            val tz = TimeZone.getTimeZone("UTC")
            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            df.timeZone = tz
            return df.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        fun fromISO8601UTC(dateStr: String): Date? {
            val tz = TimeZone.getTimeZone("UTC")
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
            df.timeZone = tz

            try {
                return df.parse(dateStr)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return null
        }
    }
}