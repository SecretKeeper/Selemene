package net.teamof.whisper.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import net.teamof.whisper.components.BottomAppBar
import net.teamof.whisper.ui.theme.WhisperTheme
import net.teamof.whisper.viewModel.*

@ExperimentalCoilApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(
) {
    val navController = rememberNavController()
    val disabledNavScreens =
        listOf("Login", "Register", "Messaging/{to_user_id}", "Profile", "Contacts/{action}")
    val conversationsActionsViewModel = hiltViewModel<ConversationActionsViewModel>()

    WhisperTheme {
        Scaffold(
            bottomBar = {
                if (!disabledNavScreens.contains(currentRoute(navController))) BottomAppBar(
                    navController,
                    conversationsActionsViewModel
                )
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
        ) {
            MainScreenNavigationConfigurations(navController, conversationsActionsViewModel)
        }
    }
}


@ExperimentalPermissionsApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController,
    conversationsActionsViewModel: ConversationActionsViewModel
) {

    val userViewModel = hiltViewModel<UserViewModel>()
    val messagesViewModel = hiltViewModel<MessagesViewModel>()
    val conversationsViewModel = hiltViewModel<ConversationsViewModel>()

    val currentUserId = userViewModel.getUserID()

    NavHost(
        navController = navController,
        startDestination = if (currentUserId == 0L) "Authentication" else "AppsDirection"
    ) {

        navigation("Login", "Authentication") {
            composable("Login") {
                LoginScreen(userViewModel, navController)
            }
            composable("Register") {
                LoginScreen(userViewModel, navController)
            }
        }

        navigation("Conversations", "AppsDirection") {
            composable("Conversations") {
                Conversations(
                    navController,
                    conversationsViewModel,
                    conversationsActionsViewModel
                )
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
                    userViewModel,
                    conversationsViewModel
                )
            }
            composable("Create") { Create(navController) }
            composable("Contacts/{action}") { backStackEntry ->
                backStackEntry.arguments?.getString("action")?.let {
                    Contacts(
                        userViewModel,
                        conversationsViewModel,
                        navController,
                        action = it
                    )
                }
            }
            composable("SelfProfile") { SelfProfile() }
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
