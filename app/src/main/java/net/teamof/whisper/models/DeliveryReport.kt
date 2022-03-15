package net.teamof.whisper.models

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class DeliveryReport(
    val ids: List<Long>
) : Serializable