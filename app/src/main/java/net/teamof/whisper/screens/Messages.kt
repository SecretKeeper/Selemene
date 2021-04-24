package net.teamof.whisper.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.skydoves.landscapist.glide.GlideImage
import net.teamof.whisper.components.MessagePortal
import net.teamof.whisper.models.MessagePortal

val data = listOf(
    MessagePortal(
        1,
        "Jaina",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        "Something in your eye?",
        "Just now",
        5
    ),
    MessagePortal(
        1,
        "Lichking",
        "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
        "Something in your eye?",
        "27mins",
        2
    ),
    MessagePortal(
        1,
        "Sylvanas Windrunner",
        "https://s1.1zoom.me/b5050/962/Elves_World_of_WarCraft_Battle_of_Azeroth_Face_574335_2560x1440.jpg",
        "Something in your eye?",
        "12:59",
        0
    )
)

@Composable
fun Messages() {
    LazyColumn {
        itemsIndexed(data) { index, item ->
            MessagePortal(item)
        }
    }
}