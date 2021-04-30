package net.teamof.whisper.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import net.teamof.whisper.components.BottomAppBar
import net.teamof.whisper.components.FloatingActionButton
import net.teamof.whisper.ui.theme.WhisperTheme

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val disabledNavScreens = listOf("messaging", "profile")


    WhisperTheme {
        Scaffold(
            bottomBar = {
                if (!disabledNavScreens.contains(currentRoute(navController))) BottomAppBar(
                    navController
                )
            },
            floatingActionButton = {
                if (!disabledNavScreens.contains(
                        currentRoute(
                            navController
                        )
                    )
                ) FloatingActionButton()
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center
        ) {
            MainScreenNavigationConfigurations(navController)
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "messages"
    ) {
        composable("messages") {
            Messages(navController)
        }

        composable(
            "messaging"
                .plus("/{username}")
        ) { backStackEntry ->
            Messaging(navController, username = backStackEntry.arguments?.getString("username")!!)
        }

        composable("feeds") { Feeds() }
        composable("create") { Create() }
        composable("contacts") { Contacts() }
        composable("profile") { Profile() }

    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}
