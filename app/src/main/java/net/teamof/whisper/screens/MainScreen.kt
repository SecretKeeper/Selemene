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
import net.teamof.whisper.components.BottomAppBar
import net.teamof.whisper.components.FloatingActionButton
import net.teamof.whisper.ui.theme.WhisperTheme
import net.teamof.whisper.utils.EchoWebSocketListener
import net.teamof.whisper.viewModel.FeedsViewModel
import okhttp3.OkHttpClient
import okhttp3.Request

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(
) {
    val navController = rememberNavController()
    val disabledNavScreens = listOf("Messaging/{channel}", "Profile", "Contacts/{action}")

    val listener = EchoWebSocketListener()
    val httpClient = OkHttpClient()
    val request = Request.Builder().url("ws://10.0.2.2:3334/ws").build()
    val websocket = httpClient.newWebSocket(request, listener)

    httpClient.dispatcher().executorService().shutdown()

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
private fun MainScreenNavigationConfigurations(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "Conversations"
    ) {
        composable("Conversations") {
            Conversations(navController)
        }
        composable(
            "Messaging"
                .plus("/{channel}")
        ) { backStackEntry ->
            Messaging(
                navController,
                channel = backStackEntry.arguments?.getString("channel")!!
            )
        }
        composable("Feeds") { backStackEntry ->
            val feedsViewModel = hiltViewModel<FeedsViewModel>()
            Feeds(feedsViewModel)
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

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
