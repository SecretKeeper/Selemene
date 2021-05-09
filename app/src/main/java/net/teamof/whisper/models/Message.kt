package net.teamof.whisper.models

data class Message(
    val id: Int,
    val user_id: Int,
    val content: String,
    val time: String,
    var selected: Boolean
)