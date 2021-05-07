package net.teamof.whisper.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import net.teamof.whisper.components.Activity
import net.teamof.whisper.models.Activity

val activities = listOf(
    Activity(
        1,
        "Sylvanas Windrunner",
        "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
        1,
    ),
    Activity(
        2,
        "Valeera",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        2,
    ),
    Activity(
        3,
        "Anduin",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        3,
    ),
    Activity(
        4,
        "Tyrande",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        1,
    )
)

@Composable
fun Activities() {
    LazyColumn {
        itemsIndexed(activities) { _, activity ->
            Activity(activity)
        }
    }
}