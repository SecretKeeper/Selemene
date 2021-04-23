package net.teamof.whisper.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.teamof.whisper.R
import androidx.compose.runtime.getValue

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
        R.drawable.ic_flash,
        false
    )
}

@Composable
fun BottomNavigation() {


    val items = listOf(
        NavItem.Messages,
        NavItem.Feeds,
        NavItem.Holder,
        NavItem.Feeds ,
        NavItem.Messages
    )



    BottomAppBar(modifier = Modifier.background(Color.Black)) {

        BottomNavigation {
            val navController = rememberNavController()
            val currentRoute = navController.currentBackStackEntry?.arguments?.getString(KEY_ROUTE)

            items.forEach { screen ->
                BottomNavigationItem(
                    selected = currentRoute == screen.route,
                    onClick = { /*TODO*/ },
                    icon = { Icon(painter = painterResource(id = R.drawable.ic_flash), contentDescription = null) },
                    enabled = screen.enabled
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