package net.teamof.whisper.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import net.teamof.whisper.R
import net.teamof.whisper.components.Contact
import net.teamof.whisper.models.Contact
import net.teamof.whisper.viewModel.UserViewModel

val contacts = listOf(
    Contact(
        1,
        1,
        "Jaina Proudmoore",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        " Lord Admiral, ruler of the Kul Tiras kingdom"
    ),
    Contact(
        2,
        3,
        "Lichking",
        "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
        "Your soul is mine"
    ),
    Contact(
        3,
        4,
        "Thrall",
        "https://cdn.hearthstonetopdecks.com/wp-content/uploads/2014/04/thrall-shaman001-640x1136.jpg",
        "Lord of the Clans"
    ),
    Contact(
        5,
        6,
        "Sylvanas Windrunner",
        "https://wallpaperchat.com/walls/full/2/2/5/402192.jpg",
        "Dark Lady, Also known Banshee Queen"
    ),
    Contact(
        6,
        7,
        "Anduin Wrynn",
        "https://mocah.org/thumbs/329166-WoW-The-Alliance-Anduin-Wrynn-Genn-Greymane-4K-iphone-wallpaper.jpg",
        "The King of the Stormwind City"
    ),
    Contact(
        7,
        8,
        "Illidan Stormrage",
        "https://images.wallpapersden.com/image/download/illidan-stormrage_a2xuaGeUmZqaraWkpJRsamWtZmhoaQ.jpg",
        "Lord of the Outland"
    ),
    Contact(
        8,
        9,
        "Tyrande Whisperwind",
        "https://coolwallpapers.me/picsup/2990861-elves-blue-hair-fantasy-art-world-of-warcraft-tyrande-whisperwind___mixed-wallpapers.jpg",
        "High Priestess of Elune"
    ),
    Contact(
        9,
        10,
        "Garrosh Hellscream",
        "https://i.pinimg.com/originals/28/a3/b7/28a3b7ced21793e17d1b9f5d5f1f3fd0.png",
        "Warlord of the Warsong clan"
    ),
    Contact(
        10,
        11,
        "Kel'thuzad",
        "https://www.mobygames.com/images/promo/original/1465476647-1936338714.jpg",
        "Founder of the Cult of the Damned"
    ),
    Contact(
        11,
        12,
        "Liadrin",
        "https://i.pinimg.com/originals/e1/81/25/e181259a1cf944de6dd8cb4d40142185.jpg",
        "Blood Knight Matriarch"
    ),
)

@ExperimentalMaterialApi
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Contacts(
    userViewModel: UserViewModel,
    navController: NavController,
    action: String
) {

    val composableScope = rememberCoroutineScope()
    val grouped = contacts.groupBy { it.username[0] }
    val searchValue = remember { mutableStateOf("") }
    val resultSearchUsers = remember { mutableListOf<Contact>() }
    LazyColumn {

        if (action == "CreateGroup")
            item {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Choose users to invite", modifier = Modifier.weight(1f))
                        TextButton(
                            shape = RoundedCornerShape(10.dp),
                            onClick = { /* Do something! */ },
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = MaterialTheme.colors.primary
//                )
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_checkmark),
                                tint = MaterialTheme.colors.primary,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(18.dp)
                                    .height(18.dp)
                            )
                            Text(
                                "Done",
                                color = MaterialTheme.colors.primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W600,
                                modifier = Modifier.padding(start = 7.dp)
                            )
                        }
                    }
                }
            }

        item {
            TextField(
                value = searchValue.value,
                onValueChange = {
                    searchValue.value = it
                    composableScope.launch {
                        userViewModel.searchUsers(it)
                    }

                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(235, 235, 235),
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = { Text(text = "Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            )
        }

        if (searchValue.value.isEmpty())

            grouped.forEach { (initial, contactsForInitial) ->
                stickyHeader {
                    Text(
                        text = initial.toString(),
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                        fontSize = 20.sp
                    )
                }

                itemsIndexed(contactsForInitial) { _, contact ->
                    Contact(navController, contact, action)
                }
            }
        else
            itemsIndexed(resultSearchUsers) { _, contact ->
                Contact(navController, contact, action)
            }

    }
}