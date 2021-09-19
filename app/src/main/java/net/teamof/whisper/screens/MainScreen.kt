package net.teamof.whisper.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import net.teamof.whisper.components.BottomAppBar
import net.teamof.whisper.components.FloatingActionButton
import net.teamof.whisper.ui.theme.WhisperTheme
import net.teamof.whisper.viewModel.ConversationsViewModel
import net.teamof.whisper.viewModel.FeedsViewModel
import net.teamof.whisper.viewModel.MessagesViewModel
import net.teamof.whisper.viewModel.UserViewModel

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(
) {
    val navController = rememberNavController()
    val disabledNavScreens =
        listOf("Login", "Messaging/{to_user_id}", "Profile", "Contacts/{action}")

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
            floatingActionButtonPosition = FabPosition.Center,
        ) {
            MainScreenNavigationConfigurations(navController)
        }
    }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun MainScreenNavigationConfigurations(navController: NavHostController) {

    val userViewModel = hiltViewModel<UserViewModel>()
    val messagesViewModel = hiltViewModel<MessagesViewModel>()
    val conversationsViewModel = hiltViewModel<ConversationsViewModel>()

    val currentUserId = userViewModel.getUserID()
        .observeAsState().value

    NavHost(
        navController = navController,
        startDestination = if (currentUserId == 0L) "Authentication" else "AppsDirection"
    ) {

        navigation("Login", "Authentication") {
            composable("Login") {
                LoginScreen(userViewModel, navController)
            }
        }

        navigation("Conversations", "AppsDirection") {
            composable("Conversations") {
                Conversations(navController, conversationsViewModel, messagesViewModel)
            }
            composable("Feeds") {
                val feedsViewModel = hiltViewModel<FeedsViewModel>()
                Feeds(feedsViewModel)
            }
            composable(
                "Messaging"
                    .plus("/{to_user_id}")
            ) { backStackEntry ->

                Messaging(
                    navController,
                    to_user_id = backStackEntry.arguments?.getString("to_user_id")!!,
                    messagesViewModel,
                    userViewModel
                )
            }
            composable("Create") { Create(navController) }
            composable("Contacts/{action}") { backStackEntry ->
                backStackEntry.arguments?.getString("action")?.let {
                    Contacts(
                        navController,
                        action = it
                    )
                }
            }
            composable("Profile") { Profile(navController) }
            composable("CreateGroup") { CreateGroup(navController) }
            composable("Activities") { Activities() }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
