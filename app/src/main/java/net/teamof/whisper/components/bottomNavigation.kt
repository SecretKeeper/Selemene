package net.teamof.whisper.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.teamof.whisper.R
import net.teamof.whisper.ui.theme.BottomNavigationHeight
import net.teamof.whisper.viewModel.ConversationActionsViewModel

sealed class NavItem(
    val route: String,
    val icon: Int,
    val enabled: Boolean
) {
    object Messages : NavItem(
        "conversations",
        R.drawable.ic_flash,
        true
    )

    object Feeds : NavItem(
        "feeds",
        R.drawable.ic_layers,
        true
    )

    object Create : NavItem(
        "create",
        R.drawable.ic_add_nav,
        false
    )

    object Activities : NavItem(
        "activities",
        R.drawable.ic_timer,
        true
    )

    object Settings : NavItem(
        "messaging",
        R.drawable.ic_settings,
        true
    )
}

@ExperimentalAnimationApi
@Composable
fun BottomAppBar(
    navController: NavController,
    conversationActionsViewModel: ConversationActionsViewModel
) {


    val items = listOf(
        NavItem.Messages,
        NavItem.Feeds,
        NavItem.Create,
        NavItem.Activities,
        NavItem.Settings
    )

    BottomAppBar(
        backgroundColor = Color(red = 245, green = 245, blue = 253),
        elevation = 0.dp,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
        modifier = Modifier.height(BottomNavigationHeight)
    ) {

        val showConversationActions: Boolean by conversationActionsViewModel.showActionsState.observeAsState(
            false
        )

        AnimatedContent(
            targetState = showConversationActions,
            transitionSpec = {
                if (showConversationActions) {
                    slideInVertically({ height -> -height }) + fadeIn() with
                            slideOutVertically({ height -> height }) + fadeOut()
                } else {
                    slideInVertically({ height -> height }) + fadeIn() with
                            slideOutVertically({ height -> -height }) + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            }
        ) { showActions ->
            if (showActions)
                ConversationActionsView(conversationActionsViewModel)
            else
                BottomNavigationView(navController, items)
        }
    }
}

@Composable
fun BottomNavigationView(navController: NavController, items: List<NavItem>) {
    BottomNavigation(
        backgroundColor = Color(red = 245, green = 245, blue = 253),
        elevation = 0.dp,
        modifier = Modifier.height(BottomNavigationHeight)
    ) {

        val currentRoute = navController.currentBackStackEntry?.destination?.route

        items.forEach { screen ->
            BottomNavigationItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo = navController.graph.startDestinationId
                        launchSingleTop = true
                    }
                },
                icon = {
                    if (screen.route != "create") Icon(
                        imageVector = ImageVector.vectorResource(id = screen.icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp, 24.dp)
                    )
                    else
                        IconButton(
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo = navController.graph.startDestinationId
                                    launchSingleTop = true
                                }
                            }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = screen.icon),
                                tint = Color.Unspecified,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                },
                enabled = screen.enabled
            )
        }
    }
}

@Composable
fun ConversationActionsView(conversationActionsViewModel: ConversationActionsViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_mute),
                    contentDescription = null,
                    modifier = Modifier
                        .width(24.dp)
                )
            }
            IconButton(onClick = {
                conversationActionsViewModel.deleteSelectedConversations()
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_trash),
                    contentDescription = null,
                    modifier = Modifier
                        .width(22.dp)
                )
            }
        }
        IconButton(onClick = {
            conversationActionsViewModel.hideActions()
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_x),
                contentDescription = null,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
        }
    }
}