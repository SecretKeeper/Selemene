package net.teamof.whisper.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.teamof.whisper.components.Feed
import net.teamof.whisper.models.Feed


val feeds = listOf(
    Feed(
        1,
        1,
        "Jaina",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        "Los Angeles, USA",
        null,
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        null,
        "Something in your eye?",
        "Just now",
    ),
    Feed(
        2,
        2,
        "Lichking",
        "https://s1.1zoom.me/b5050/962/Elves_World_of_WarCraft_Battle_of_Azeroth_Face_574335_2560x1440.jpg",
        "Tokyo, Japan",
        null,
        null,
        null,
        "Something in your eye?",
        "27mins",
    ),
    Feed(
        1,
        3,
        "Sylvanas Windrunner",
        "https://s1.1zoom.me/b5050/962/Elves_World_of_WarCraft_Battle_of_Azeroth_Face_574335_2560x1440.jpg",
        "Ghale Gabri, Iran",
        null,
        "https://s1.1zoom.me/b5050/962/Elves_World_of_WarCraft_Battle_of_Azeroth_Face_574335_2560x1440.jpg",
        null,
        "Something in your eye?",
        "12:59",
    )
)

@Composable
fun Feeds() {
    LazyColumn(
        Modifier
            .background(Color(red = 245, green = 245, blue = 253))
            .padding(top = 70.dp, bottom = 70.dp, start = 15.dp, end = 15.dp)
    ) {
        itemsIndexed(feeds) { _, item ->
            Feed(item)
        }
    }
}