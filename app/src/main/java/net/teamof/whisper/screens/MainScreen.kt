package net.teamof.whisper.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.teamof.whisper.components.BottomAppBar
import net.teamof.whisper.components.FloatingActionButton
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.ui.theme.WhisperTheme
import net.teamof.whisper.viewModel.ConversationsViewModel

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
    navController: NavHostController,
    conversationsViewModel: ConversationsViewModel = viewModel()
) {
    
    val messages: List<Conversation> by conversationsViewModel.conversations.observeAsState(listOf())

    NavHost(
        navController = navController,
        startDestination = "Messages"
    ) {
        composable("Messages") {
            Messages(navController, messages)
        }
        composable(
            "Messaging"
                .plus("/{user_id}")
        ) { backStackEntry ->
            Messaging(navController, user_id = backStackEntry.arguments?.getString("user_id")!!)
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
        composable("Profile") { Profile(navController) }
        composable("CreateGroup") { CreateGroup(navController) }
        composable("Activities") { Activities() }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
