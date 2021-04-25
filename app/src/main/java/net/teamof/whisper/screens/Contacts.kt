package net.teamof.whisper.screens

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
        "Jaina",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        "No Battrey",
        true
    ),
    Contact(
        3,
        "Jaina Pro",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        "No Way to do this!",
        true
    ),
    Contact(
        2,
        "Lichking",
        "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
        "Your soul is mine",
        true
    ),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Contacts() {

    val grouped = contacts.groupBy { it.username[0] }

    LazyColumn {


        grouped.forEach { (initial, contactsForInitial) ->

            stickyHeader {
                Text(
                    text = initial.toString(),
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                    fontSize = 20.sp
                )
            }

            itemsIndexed(contactsForInitial) { index, contact ->
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