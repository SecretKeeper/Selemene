package net.teamof.whisper.screens

import androidx.compose.animation.ExperimentalAnimationApi
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

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val disabledNavScreens = listOf("Messaging/{username}", "Profile", "Contacts/{action}")

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
                    ) FloatingActionButton(navController)
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center
        ) {
            MainScreenNavigationConfigurations(navController)
        }
    }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "Messages"
    ) {
        composable("Messages") {
            Messages(navController)
        }
        composable(
            "Messaging"
                .plus("/{username}")
        ) { backStackEntry ->
            Messaging(navController, username = backStackEntry.arguments?.getString("username")!!)
        }
        composable("Feeds") { Feeds() }
        composable("Create") { Create(navController) }
        composable("Contacts/{action}") { backStackEntry ->
            backStackEntry.arguments?.getString("action")?.let {
                Contacts(
                    action = it
                )
            }
        }
        composable("Profile") { Profile() }
        composable("CreateGroup") { CreateGroup(navController) }
        composable("Activities") { Activities() }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}
