package net.teamof.whisper.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.teamof.whisper.R

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

    object Holder : NavItem(
        "holder",
        R.drawable.ic_settings,
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

@Composable
fun BottomAppBar(navController: NavController) {


    val items = listOf(
        NavItem.Messages,
        NavItem.Feeds,
        NavItem.Holder,
        NavItem.Activities,
        NavItem.Settings
    )

    BottomAppBar(
        backgroundColor = Color(red = 245, green = 245, blue = 253),
        elevation = 0.dp,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
        modifier = Modifier.height(65.dp)
    ) {
        BottomNavigation(
            backgroundColor = Color(red = 245, green = 245, blue = 253),
            elevation = 0.dp,
            modifier = Modifier.height(65.dp)
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
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp, 24.dp)
                        )
                    },
                    enabled = screen.enabled,
                )
            }
        }
    }
}

@Composable
fun FloatingActionButton(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigate("create")
        },
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .offset(Dp(0F), Dp(33F))
            .size(45.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .width(25.dp)
                .height(25.dp)
        )
    }
}