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
import net.teamof.whisper.models.Comment
import net.teamof.whisper.models.Feed


val feeds = listOf(
    Feed(
        1,
        "Jaina",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        "Los Angeles, USA",
        null,
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        null,
        "Something in your eye?",
        "Just now",
        listOf(
            Comment(
                1,
                "Lichking",
                "https://s1.1zoom.me/b5050/962/Elves_World_of_WarCraft_Battle_of_Azeroth_Face_574335_2560x1440.jpg",
                "otherwise, you could use the flags in the manifest. See here for more information",
                "12 mins"
            ),
            Comment(
                2,
                "Sylvanas Windrunner",
                "https://s1.1zoom.me/b5050/962/Elves_World_of_WarCraft_Battle_of_Azeroth_Face_574335_2560x1440.jpg",
                "otherwise, you could use the flags in the manifest. See here for more information",
                "19 hrs ago"
            )
        )
    ),
    Feed(
        2,
        "Lichking",
        "https://s1.1zoom.me/b5050/962/Elves_World_of_WarCraft_Battle_of_Azeroth_Face_574335_2560x1440.jpg",
        "Tokyo, Japan",
        null,
        null,
        null,
        "Something in your eye?",
        "27mins",
        listOf()
    ),
    Feed(
        3,
        "Sylvanas Windrunner",
        "https://s1.1zoom.me/b5050/962/Elves_World_of_WarCraft_Battle_of_Azeroth_Face_574335_2560x1440.jpg",
        "Ghale Gabri, Iran",
        null,
        "https://s1.1zoom.me/b5050/962/Elves_World_of_WarCraft_Battle_of_Azeroth_Face_574335_2560x1440.jpg",
        null,
        "Something in your eye?",
        "12:59",
        listOf()
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