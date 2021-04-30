package net.teamof.whisper.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import net.teamof.whisper.components.MessagePortal
import net.teamof.whisper.models.MessagePortal

val messages = listOf(
    MessagePortal(
        1,
        "Jaina",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        "Something in your eye?",
        "Just now",
        5
    ),
    MessagePortal(
        2,
        "Lichking",
        "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
        "Something in your eye?",
        "27mins",
        2
    ),
    MessagePortal(
        3,
        "Sylvanas Windrunner",
        "https://s1.1zoom.me/b5050/962/Elves_World_of_WarCraft_Battle_of_Azeroth_Face_574335_2560x1440.jpg",
        "Something in your eye?",
        "12:59",
        0
    ),
    MessagePortal(
        3,
        "Duel",
        "https://a-static.besthdwallpaper.com/world-of-warcraft-wow-valeera-sanguinar-wallpaper-55768_L.jpg",
        "Something in your eye?",
        "12:59",
        0
    )
)

@Composable
fun Messages() {
    LazyColumn {
        itemsIndexed(messages) { index, item ->
            MessagePortal(item)
        }
    }
}