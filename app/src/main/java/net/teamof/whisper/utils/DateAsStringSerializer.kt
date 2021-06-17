package net.teamof.whisper.utils


import android.annotation.SuppressLint
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateAsStringSerializer : KSerializer<Date> {
    @SuppressLint("SimpleDateFormat")
    private val df: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS")

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(df.format(value.time))
    }

    override fun deserialize(decoder: Decoder): Date {
        return df.parse(decoder.decodeString())
    }

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)
}
