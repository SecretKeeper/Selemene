package net.teamof.whisper.models

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class DeliveryReport(
    val sender: Long? = 0,
    val ids: List<Long>
) : Serializable