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
import net.teamof.whisper.models.Counters
import net.teamof.whisper.models.Profile
import net.teamof.whisper.models.UserAPI
import net.teamof.whisper.viewModel.ProfileViewModel
import net.teamof.whisper.viewModel.UserViewModel

val contacts = listOf(
    UserAPI(
        1,
        "Jaina Proudmoore",
        "jaina.proudmoore@gmail.com",
        "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
        Profile(
            status = "Lord Admiral, ruler of the Kul Tiras kingdom"
        ),
        Counters(
            feeds = 114,
            followers = 846
        )
    ),
    UserAPI(
        2,
        "Lichking",
        "lichking@gmail.com",
        "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
        Profile(
            status = "Your soul is mine"
        ),
        Counters(
            feeds = 14,
            followers = 114
        )
    ),
    UserAPI(
        1,
        "Thrall",
        "thrall@gmail.com",
        "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
        Profile(
            status = "Lord of the Clans"
        ),
        Counters(
            feeds = 14,
            followers = 114
        )
    ),
    UserAPI(
        2,
        "Thrall",
        "thrall@gmail.com",
        "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
        Profile(
            status = "Dark Lady, Also known Banshee Queen"
        ),
        Counters(
            feeds = 14,
            followers = 114
        )
    ),
    UserAPI(
        1,
        "Sylvanas Windrunner",
        "sylvanas.windrunner@gmail.com",
        "https://wallpaperchat.com/walls/full/2/2/5/402192.jpg",
        Profile(
            status = "Dark Lady, Also known Banshee Queen"
        ),
        Counters(
            feeds = 26,
            followers = 4870
        )
    ),
    UserAPI(
        2,
        "Anduin Wrynn",
        "anduin.wrynn@gmail.com",
        "https://mocah.org/thumbs/329166-WoW-The-Alliance-Anduin-Wrynn-Genn-Greymane-4K-iphone-wallpaper.jpg",
        Profile(
            status = "The King of the Stormwind City"
        ),
        Counters(
            feeds = 9,
            followers = 1702
        )
    ),
    UserAPI(
        1,
        "Illidan Stormrage",
        "illidan.stormrage@gmail.com",
        "https://images.wallpapersden.com/image/download/illidan-stormrage_a2xuaGeUmZqaraWkpJRsamWtZmhoaQ.jpg",
        Profile(
            status = "Lord of the Outland"
        ),
        Counters(
            feeds = 78,
            followers = 3202
        )
    ),
    UserAPI(
        2,
        "Tyrande Whisperwind",
        "tyrande.whisperwind@gmail.com",
        "https://coolwallpapers.me/picsup/2990861-elves-blue-hair-fantasy-art-world-of-warcraft-tyrande-whisperwind___mixed-wallpapers.jpg",
        Profile(
            status = "High Priestess of Elune"
        ),
        Counters(
            feeds = 236,
            followers = 10502
        )
    ),
    UserAPI(
        1,
        "Kel'thuzad",
        "kelthuzad@gmail.com",
        "https://www.mobygames.com/images/promo/original/1465476647-1936338714.jpg",
        Profile(
            status = "Founder of the Cult of the Damned"
        ),
        Counters(
            feeds = 8,
            followers = 202
        )
    ),
    UserAPI(
        2,
        "Liadrin",
        "liadrin@gmail.com",
        "https://i.pinimg.com/originals/e1/81/25/e181259a1cf944de6dd8cb4d40142185.jpg",
        Profile(
            status = "Blood Knight Matriarch"
        ),
        Counters(
            feeds = 46,
            followers = 522
        )
    ),
)

@ExperimentalMaterialApi
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Contacts(
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController,
    action: String
) {

    val composableScope = rememberCoroutineScope()
    val grouped = contacts.groupBy { it.username[0] }
    val searchValue = remember { mutableStateOf("") }
    var resultSearchUsers = remember { mutableListOf<UserAPI>() }
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
                onValueChange = { it ->
                    searchValue.value = it
                    composableScope.launch {
                        userViewModel.searchUsers(it) {
                            resultSearchUsers =
                                it as MutableList<UserAPI>
                        }
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
                    Contact(navController, profileViewModel, contact, action)
                }
            }
        else
            itemsIndexed(resultSearchUsers) { _, user ->
                Contact(navController, profileViewModel, user, action)
            }

    }
}