package net.teamof.whisper.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.rememberNavController
import net.teamof.whisper.R

sealed class NavItem(
    val route: String,
    val title: String,
    val icon: Int,
    val enabled: Boolean
) {
    object Messages: NavItem(
        "messages",
        "Messages",
        R.drawable.ic_flash,
        true
    )

    object Feeds: NavItem(
        "feeds",
        "Feeds",
        R.drawable.ic_layers,
        true
    )

    object Holder: NavItem(
        "holder",
        "Holder",
        R.drawable.ic_settings,
        false
    )

    object Activities: NavItem(
        "activities",
        "Activities",
        R.drawable.ic_timer,
        true
    )

    object Settings: NavItem(
        "settings",
        "Settings",
        R.drawable.ic_settings,
        true
    )
}

@Composable
fun BottomNavigation() {


    val items = listOf(
        NavItem.Messages,
        NavItem.Feeds,
        NavItem.Holder,
        NavItem.Activities ,
        NavItem.Settings
    )



    BottomAppBar(modifier = Modifier.background(Color.Black)) {

        BottomNavigation(modifier = Modifier.background(Color.Black)) {
            val navController = rememberNavController()
            val currentRoute = navController.currentBackStackEntry?.arguments?.getString(KEY_ROUTE)

            items.forEach { screen ->
                BottomNavigationItem(
                    selected = currentRoute == screen.route,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp, 28.dp))
                        },
                    enabled = screen.enabled,
                )
            }
        }
    }
}

@Composable
fun floatingActionButton() {
    FloatingActionButton(onClick = { /*TODO*/ }, modifier = Modifier.offset(Dp(0F), Dp(25F))) {
        Icon(painter = painterResource(id = R.drawable.ic_flash), contentDescription = null)
    }
}