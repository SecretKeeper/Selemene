package net.teamof.whisper.models

data class Feed(
    val user_id: Int,
    val username: String,
    val user_image: String,
    val location: String?,
    val category: String?,
    val image: String?,
    val title: String?,
    val content: String,
    val time: String,
    val comments: List<Comment>
)

data class Comment(
    val user_id: Int,
    val username: String,
    val user_image: String,
    val content: String,
    val time: String,
)


