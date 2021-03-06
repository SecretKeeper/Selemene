package net.teamof.whisper.screens


import android.content.res.Resources
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import net.teamof.whisper.R
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.ProfileViewModel


val gridImages = listOf(
	"https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
	"https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
	"https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
	"https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
	"https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
	"https://million-wallpapers.com/wallpapers/1/65/16175274567205625182.jpg",
	"https://c4.wallpaperflare.com/wallpaper/607/463/825/world-of-warcraft-jaina-proudmoore-magic-mazert-young-turquoise-hd-wallpaper-preview.jpg",
)


@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Profile(
	navController: NavController,
	profileViewModel: ProfileViewModel,
) {

	val user = profileViewModel.userState.observeAsState()
	val displayMetrics = Resources.getSystem().displayMetrics
	val heightDp = displayMetrics.heightPixels / displayMetrics.density

	val scaffoldState = rememberBottomSheetScaffoldState()

	val expanded = remember { mutableStateOf(false) }

	val scaleFab = remember { Animatable(1F) }
	LaunchedEffect(
		key1 = scaffoldState.bottomSheetState.isAnimationRunning,
		key2 = scaffoldState.bottomSheetState.isExpanded
	) {
		scaleFab.animateTo(
			if (
				scaffoldState.bottomSheetState.isAnimationRunning ||
				scaffoldState.bottomSheetState.isExpanded
			)
				0.001F else 1F
		)
	}

	BottomSheetScaffold(
		sheetContent = {
			LazyColumn() {
				item {
					Column(modifier = Modifier.padding(vertical = 25.dp, horizontal = 25.dp)) {
						Text(
							text = user.value?.user?.username ?: "",
							fontSize = 18.sp,
							fontFamily = fontFamily,
							fontWeight = FontWeight.SemiBold,
							color = Color.DarkGray,
							modifier = Modifier.padding(bottom = 10.dp)
						)
						if (user.value!!.profile.profile.status != "")
							Text(
								text = user.value!!.profile.profile.status ?: "",
								fontSize = 14.sp,
								fontFamily = fontFamily,
								fontWeight = FontWeight.SemiBold,
								color = Color(red = 23, green = 155, blue = 128),
								lineHeight = 20.sp,
								modifier = Modifier.padding(bottom = 10.dp)
							)
						Row {
							ClickableText(
								text = AnnotatedString(
									"${user.value!!.profile.userCounters.feeds} Feeds",
									spanStyle = SpanStyle(
										fontSize = 14.sp,
										fontFamily = fontFamily,
										fontWeight = FontWeight.SemiBold,
										color = MaterialTheme.colors.primary
									)
								),
								onClick = {},
								modifier = Modifier.padding(vertical = 0.dp)
							)
							Divider(
								modifier = Modifier
									.fillMaxHeight()
									.width(10.dp)
									.padding(horizontal = 15.dp)
							)
							ClickableText(
								text = AnnotatedString(
									"${user.value!!.profile.userCounters.followers} Followers",
									spanStyle = SpanStyle(
										fontSize = 14.sp,
										fontFamily = fontFamily,
										fontWeight = FontWeight.SemiBold,
										color = MaterialTheme.colors.primary
									)
								),
								onClick = {},
								modifier = Modifier.padding(vertical = 0.dp)
							)
						}

						Text(
							text = user.value!!.profile.profile.description ?: "",
							color = Color(
								red = 130, green = 130, blue = 130
							),
							fontFamily = fontFamily,
							fontWeight = FontWeight.Normal,
							fontSize = 14.sp,
							lineHeight = 20.sp,
							modifier = Modifier.padding(vertical = 15.dp)
						)

						Divider(modifier = Modifier.padding(vertical = 15.dp))
						Row(verticalAlignment = Alignment.CenterVertically) {
							AsyncImage(
								model = "https://upload.wikimedia.org/wikipedia/en/thumb/d/d3/Starbucks_Corporation_Logo_2011.svg/1200px-Starbucks_Corporation_Logo_2011.svg.png",
								contentDescription = null,
								contentScale = ContentScale.Crop,
								modifier = Modifier
									.width(90.dp)
									.height(90.dp)
									.clip(CircleShape)
							)
							Column(Modifier.padding(start = 10.dp)) {
								Text(
									text = "Starbucks Major Guild",
									color = MaterialTheme.colors.onPrimary,
									fontFamily = fontFamily,
									fontWeight = FontWeight.SemiBold,
									modifier = Modifier.padding(bottom = 5.dp)
								)
								Text(
									text = "Rank 2",
									color = MaterialTheme.colors.onSecondary,
									fontSize = 14.sp,
									fontFamily = fontFamily,
									fontWeight = FontWeight.Medium,
									modifier = Modifier.padding(bottom = 5.dp)
								)
								Text(
									text = "546 Members",
									color = MaterialTheme.colors.onSecondary,
									fontSize = 14.sp,
									fontFamily = fontFamily,
									fontWeight = FontWeight.Medium,
								)
							}
						}
						Divider(Modifier.padding(vertical = 15.dp))
						Row(Modifier.padding(vertical = 15.dp)) {
							Text(
								text = "Phone",
								color = MaterialTheme.colors.onSecondary,
								fontSize = 14.sp,
								fontFamily = fontFamily,
								fontWeight = FontWeight.Normal,
								lineHeight = 20.sp,
								modifier = Modifier
									.padding(end = 35.dp)
									.weight(0.4f)
							)
							Text(
								text = "+989339419119",
								color = MaterialTheme.colors.onSecondary,
								fontSize = 14.sp,
								fontFamily = fontFamily,
								fontWeight = FontWeight.Normal,
								lineHeight = 20.sp,
								modifier = Modifier.weight(1f)
							)
						}
						Row(Modifier.padding(vertical = 15.dp)) {
							Text(
								text = "Location",
								color = MaterialTheme.colors.onSecondary,
								fontFamily = fontFamily,
								fontWeight = FontWeight.Normal,
								fontSize = 14.sp,
								lineHeight = 20.sp,
								modifier = Modifier
									.padding(end = 35.dp)
									.weight(0.4f)
							)
							Text(
								text = "Tottenham, England",
								color = MaterialTheme.colors.onSecondary,
								fontFamily = fontFamily,
								fontWeight = FontWeight.Normal,
								fontSize = 14.sp,
								lineHeight = 20.sp,
								modifier = Modifier.weight(1f)
							)
						}
						Row(Modifier.padding(vertical = 15.dp)) {
							Text(
								text = "Last Seen",
								color = MaterialTheme.colors.onSecondary,
								fontSize = 14.sp,
								fontFamily = fontFamily,
								fontWeight = FontWeight.Normal,
								lineHeight = 20.sp,
								modifier = Modifier
									.padding(end = 35.dp)
									.weight(0.4f)
							)
							Text(
								text = "2 hours ago",
								color = MaterialTheme.colors.onSecondary,
								fontSize = 14.sp,
								fontFamily = fontFamily,
								fontWeight = FontWeight.Normal,
								lineHeight = 20.sp,
								modifier = Modifier.weight(1f)
							)
						}
						Row(Modifier.padding(vertical = 15.dp)) {
							Text(
								text = "Groups",
								color = MaterialTheme.colors.onSecondary,
								fontSize = 14.sp,
								fontFamily = fontFamily,
								fontWeight = FontWeight.Normal,
								lineHeight = 20.sp,
								modifier = Modifier
									.padding(end = 35.dp)
									.weight(0.4f)
							)
							Column(modifier = Modifier.weight(1f)) {
								Text(
									text = "Global Anime Chat",
									color = MaterialTheme.colors.onSecondary,
									fontSize = 14.sp,
									fontFamily = fontFamily,
									fontWeight = FontWeight.Normal,
									lineHeight = 20.sp,
									modifier = Modifier.padding(bottom = 15.dp)
								)
								Text(
									text = "Margin Whales",
									color = MaterialTheme.colors.onSecondary,
									fontSize = 14.sp,
									fontFamily = fontFamily,
									fontWeight = FontWeight.Normal,
									lineHeight = 20.sp,
									modifier = Modifier.padding(bottom = 15.dp)
								)
								Text(
									text = "Vahid Azizi Studio",
									color = MaterialTheme.colors.onSecondary,
									fontSize = 14.sp,
									fontFamily = fontFamily,
									fontWeight = FontWeight.Normal,
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
								fontFamily = fontFamily,
								fontWeight = FontWeight.Medium,
								modifier = Modifier
									.weight(1f)
									.wrapContentWidth(Alignment.Start)
							)
							ClickableText(
								text = AnnotatedString(
									"Show More",
									spanStyle = SpanStyle(
										fontSize = 13.sp,
										fontFamily = fontFamily,
										fontWeight = FontWeight.Medium,
										color = MaterialTheme.colors.primary
									)
								),
								onClick = {},
								modifier = Modifier
									.weight(1f)
									.wrapContentWidth(Alignment.End)
							)
						}
//						LazyRow() {
//							itemsIndexed(gridImages) { _, image ->
//								AsyncImage(
//									model = image,
//									contentDescription = null,
//									contentScale = ContentScale.Crop,
//									modifier = Modifier
//										.width(100.dp)
//										.height(100.dp)
//								)
//							}
//						}
						Divider(Modifier.padding(vertical = 15.dp))
					} // Column Container
				}
			}
		},
		scaffoldState = scaffoldState,
		floatingActionButton = {
			FloatingActionButton(
				backgroundColor = MaterialTheme.colors.primary,
				elevation = FloatingActionButtonDefaults.elevation(0.dp),
				onClick = {
					profileViewModel.getUserWithProfileByIdBeforeNavigate(user.value!!.user.userId) {
						navController.navigate("Messaging/${user.value!!.user.userId}") {
							launchSingleTop = true
						}
					}
				},
				modifier = Modifier.scale(scaleFab.value)
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
		AsyncImage(
			model = user.value?.user?.avatar ?: "",
			contentDescription = null,
			modifier = Modifier
				.fillMaxWidth()
				.height(((heightDp * 59) / 100).dp),
			contentScale = ContentScale.FillWidth
		)
		Row(
			Modifier
				.offset(y = (-(heightDp * 57) / 100).dp)
				.padding(horizontal = 10.dp)
		) {
			IconButton(onClick = { navController.navigateUp() }) {
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
								Text(
									"Clear History",
									fontSize = 14.sp,
									modifier = Modifier.padding(start = 15.dp, end = 10.dp)
								)
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
								Text(
									"Add Contact",
									fontSize = 14.sp,
									modifier = Modifier.padding(start = 15.dp, end = 10.dp)
								)
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
								Text(
									"Block",
									fontSize = 14.sp,
									modifier = Modifier.padding(start = 15.dp, end = 10.dp)
								)
							}
						}
					}
				}
			}
		}
	}
}