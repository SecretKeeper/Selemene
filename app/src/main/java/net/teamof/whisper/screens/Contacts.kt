package net.teamof.whisper.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.teamof.whisper.components.Contact
import net.teamof.whisper.models.Contact

val contacts = listOf(
    Contact(
        1,
        "Jaina Proudmoore",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        " Lord Admiral, ruler of the Kul Tiras kingdom",
        true,
        true
    ),
    Contact(
        3,
        "Lichking",
        "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
        "Your soul is mine",
        true,
        false
    ),
    Contact(
        4,
        "Thrall",
        "https://image3.uhdpaper.com/wallpaper-hd/thrall-world-of-warcraft-battle-for-azeroth-orc-uhdpaper.com-hd-3.978.jpg",
        "Lord of the Clans",
        true,
        false
    ),
    Contact(
        5,
        "Thrall",
        "https://image3.uhdpaper.com/wallpaper-hd/thrall-world-of-warcraft-battle-for-azeroth-orc-uhdpaper.com-hd-3.978.jpg",
        "Lord of the Clans",
        true,
        false
    ),
    Contact(
        6,
        "Sylvanas Windrunner",
        "https://image3.uhdpaper.com/wallpaper-hd/thrall-world-of-warcraft-battle-for-azeroth-orc-uhdpaper.com-hd-3.978.jpg",
        "Dark Lady, Also known Banshee Queen",
        true,
        false
    ),
    Contact(
        7,
        "Anduin Wrynn",
        "https://c4.wallpaperflare.com/wallpaper/521/133/218/alliance-anduin-wrynn-armour-blonde-wallpaper-preview.jpg",
        "The King of the Stormwind City",
        true,
        false
    ),
    Contact(
        8,
        "Illidan Stormrage",
        "https://c4.wallpaperflare.com/wallpaper/805/742/532/warcraft-world-of-warcraft-demon-illidan-stormrage-night-elf-hd-wallpaper-preview.jpg",
        "Lord of the Outland",
        true,
        false
    ),
    Contact(
        9,
        "Tyrande Whisperwind",
        "https://coolwallpapers.me/picsup/2990861-elves-blue-hair-fantasy-art-world-of-warcraft-tyrande-whisperwind___mixed-wallpapers.jpg",
        "High Priestess of Elune",
        true,
        false
    ),
    Contact(
        10,
        "Garrosh Hellscream",
        "https://i.pinimg.com/originals/28/a3/b7/28a3b7ced21793e17d1b9f5d5f1f3fd0.png",
        "Warlord of the Warsong clan",
        true,
        false
    ),
    Contact(
        11,
        "Kel'thuzad",
        " https://i.ytimg.com/vi/uNeMsENbeJw/hqdefault.jpg",
        "Founder of the Cult of the Damned",
        true,
        false
    ),
    Contact(
        12,
        "Liadrin",
        "https://i.pinimg.com/originals/e1/81/25/e181259a1cf944de6dd8cb4d40142185.jpg",
        "Blood Knight Matriarch",
        true,
        false
    ),
)

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Contacts() {

    val grouped = contacts.groupBy { it.username[0] }

    LazyColumn(modifier = Modifier.padding(bottom = 80.dp)) {
        grouped.forEach { (initial, contactsForInitial) ->

            stickyHeader {
                Text(
                    text = initial.toString(),
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                    fontSize = 20.sp
                )
            }

            itemsIndexed(contactsForInitial) { _, contact ->
                Contact(contact)
            }
        }
    }
}

@Preview
@Composable
fun ContactsPreview() {
    Contacts()
}