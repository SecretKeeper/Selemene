package net.teamof.whisper.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.teamof.whisper.components.ImageFeed
import net.teamof.whisper.components.MessagePortal
import net.teamof.whisper.components.TextFeed
import net.teamof.whisper.models.Feed


val feeds = listOf(
    Feed(
        1,
        1,
        "Jaina",
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
        "Tokyo, Japan",
        null,
        "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
        null,
        "Something in your eye?",
        "27mins",
    ),
    Feed(
        1,
        3,
        "Sylvanas Windrunner",
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
    LazyColumn(Modifier.padding(bottom = 80.dp)) {
        itemsIndexed(feeds) { index, item ->
            if(item.type == 1) {
                ImageFeed(item)
            } else if (item.type == 2) {
                TextFeed(item)
            }
        }
    }
}