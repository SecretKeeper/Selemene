package net.teamof.whisper.models

data class Feed(
    val type: Int,
    val user_id: Int,
    val username: String,
    val location: String?,
    val category: String?,
    val image: String,
    val title: String?,
    val content: String,
    val time: String
)
