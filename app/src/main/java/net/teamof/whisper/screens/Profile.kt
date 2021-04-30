package net.teamof.whisper.screens


import android.content.res.Resources
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import kotlinx.coroutines.launch
import net.teamof.whisper.R


val gridImages = listOf(
    "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
    "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
    "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
    "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
    "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
    "https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
    "https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
)


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Profile() {

    val displayMetrics = Resources.getSystem().displayMetrics
    val heightDp = displayMetrics.heightPixels / displayMetrics.density

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    val expanded = remember { mutableStateOf(false) }

    BottomSheetScaffold(
        sheetContent = {
            LazyColumn() {
                item {
                    Column(modifier = Modifier.padding(vertical = 25.dp, horizontal = 25.dp)) {
                        Text(
                            text = "Jaina Proudmoore",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "Don't let them take your mind",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            color = Color(red = 23, green = 155, blue = 128),
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Row {
                            ClickableText(
                                text = AnnotatedString(
                                    "223 Feeds",
                                    spanStyle = SpanStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W600,
                                        color = MaterialTheme.colors.primary
                                    )
                                ),
                                onClick = {},
                                modifier = Modifier.padding(vertical = 0.dp)
                            )
                            Divider(
//                                color = MaterialTheme.colors.primary,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(10.dp)
                                    .padding(horizontal = 15.dp)
                            )
                            ClickableText(
                                text = AnnotatedString(
                                    "209 Followers",
                                    spanStyle = SpanStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W600,
                                        color = MaterialTheme.colors.primary
                                    )
                                ),
                                onClick = {},
                                modifier = Modifier.padding(vertical = 0.dp)
                            )
                        }

                        for (i in 1..3) {
                            Text(
                                text = "We provide guests with everything needed for a relaxing holiday. With on-site spa services, a state-of-the-art fitness center and endless leisure activities.",
                                color = Color(red = 130, green = 130, blue = 130),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )
                        }

                        Divider(modifier = Modifier.padding(vertical = 15.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = rememberGlidePainter(request = "https://upload.wikimedia.org/wikipedia/en/thumb/d/d3/Starbucks_Corporation_Logo_2011.svg/1200px-Starbucks_Corporation_Logo_2011.svg.png",
                                    requestBuilder = { apply(RequestOptions().circleCrop()) }),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(90.dp)
                                    .height(90.dp)
                                    .padding(horizontal = 5.dp)
                            )
                            Column(Modifier.padding(start = 10.dp)) {
                                Text(
                                    text = "Starbucks Major Guild",
                                    color = MaterialTheme.colors.onPrimary,
                                    fontWeight = FontWeight.W600,
                                    modifier = Modifier.padding(bottom = 5.dp)
                                )
                                Text(
                                    text = "Rank 2",
                                    color = MaterialTheme.colors.onSecondary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W500,
                                    modifier = Modifier.padding(bottom = 5.dp)
                                )
                                Text(
                                    text = "546 Members",
                                    color = MaterialTheme.colors.onSecondary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W500
                                )
                            }
                        }
                        Divider(Modifier.padding(vertical = 15.dp))
                        Row(Modifier.padding(vertical = 15.dp)) {
                            Text(
                                text = "Phone",
                                color = Color(red = 130, green = 130, blue = 130),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier
                                    .padding(end = 35.dp)
                                    .weight(0.4f)
                            )
                            Text(
                                text = "+989339419119",
                                color = Color(red = 130, green = 130, blue = 130),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(Modifier.padding(vertical = 15.dp)) {
                            Text(
                                text = "Location",
                                color = Color(red = 130, green = 130, blue = 130),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier
                                    .padding(end = 35.dp)
                                    .weight(0.4f)
                            )
                            Text(
                                text = "Tottenham, England",
                                color = Color(red = 130, green = 130, blue = 130),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(Modifier.padding(vertical = 15.dp)) {
                            Text(
                                text = "Last Seen",
                                color = Color(red = 130, green = 130, blue = 130),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier
                                    .padding(end = 35.dp)
                                    .weight(0.4f)
                            )
                            Text(
                                text = "2 hours ago",
                                color = Color(red = 130, green = 130, blue = 130),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(Modifier.padding(vertical = 15.dp)) {
                            Text(
                                text = "Groups",
                                color = Color(red = 130, green = 130, blue = 130),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier
                                    .padding(end = 35.dp)
                                    .weight(0.4f)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Global Anime Chat",
                                    color = Color(red = 130, green = 130, blue = 130),
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(bottom = 15.dp)
                                )
                                Text(
                                    text = "Margin Whales",
                                    color = Color(red = 130, green = 130, blue = 130),
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(bottom = 15.dp)
                                )
                                Text(
                                    text = "Vahid Azizi Studio",
                                    color = Color(red = 130, green = 130, blue = 130),
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(bottom = 15.dp)
                                )
                            }
                        }
                        Divider(Modifier.padding(vertical = 15.dp))
                        Row(Modifier.padding(bottom = 5.dp)) {
                            Text(
                                text = "Shared Media",
                                color = Color.DarkGray,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.W600,
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentWidth(Alignment.Start)
                            )
                            ClickableText(
                                text = AnnotatedString(
                                    "Show More",
                                    spanStyle = SpanStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.W600,
                                        color = MaterialTheme.colors.primary
                                    )
                                ),
                                onClick = {},
                                modifier = Modifier.weight(1f)
                                    .wrapContentWidth(Alignment.End)
                            )
                        }
                        LazyRow() {
                            itemsIndexed(gridImages) { _, image ->
                                Image(
                                    painter = rememberGlidePainter(request = image,
                                        requestBuilder = { apply(RequestOptions().centerCrop()) }),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(100.dp)
                                        .padding(end = 10.dp)
                                )
                            }
                        }
                        Divider(Modifier.padding(vertical = 15.dp))
                    } // Column Container
                }
            }
        },
        scaffoldState = scaffoldState,
        floatingActionButton = {
            if (scaffoldState.bottomSheetState.isCollapsed) FloatingActionButton(
                backgroundColor = MaterialTheme.colors.primary,
                onClick = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Snackbar")
                    }
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_flash),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        sheetPeekHeight = ((heightDp * 42) / 100).dp,
    ) {
        Image(
            painter = rememberGlidePainter(request = "https://uupload.ir/files/2fk9_6087f98a3cd34_(2).jpg"),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(((heightDp * 58) / 100).dp)
        )
        Row(
            Modifier
                .offset(y = (-(heightDp * 58) / 100).dp)
                .padding(vertical = 15.dp, horizontal = 10.dp)
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_chevron_left),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.wrapContentSize(Alignment.CenterEnd)) {
                    IconButton(onClick = { expanded.value = !expanded.value }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_more_vertical),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        DropdownMenuItem(onClick = { /* Handle refresh! */ }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_eraser),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(25.dp)
                                        .height(25.dp)
                                )
                                Text("Clear History", modifier = Modifier.padding(start = 15.dp))
                            }
                        }
                        DropdownMenuItem(onClick = { /* Handle settings! */ }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_blocked),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(23.dp)
                                        .height(23.dp)
                                )
                                Text("Block", modifier = Modifier.padding(start = 15.dp))
                            }
                        }
                        DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_user),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(24.dp)
                                        .height(24.dp)
                                )
                                Text("Add Contact", modifier = Modifier.padding(start = 15.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}