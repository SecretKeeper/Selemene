package net.teamof.whisper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import net.teamof.whisper.components.BottomAppBar
import net.teamof.whisper.components.FloatingActionButton
import net.teamof.whisper.screens.*
import net.teamof.whisper.ui.theme.WhisperTheme


class MainActivity : ComponentActivity() {

    private val DisbaledNavScreens = listOf("messaging", "profile")

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            WhisperTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        bottomBar = {
                            if (!DisbaledNavScreens.contains(currentRoute(navController))) BottomAppBar(
                                navController
                            )
                        },
                        floatingActionButton = {
                            if (!DisbaledNavScreens.contains(
                                    currentRoute(
                                        navController
                                    )
                                )
                            ) FloatingActionButton()
                        },
                        isFloatingActionButtonDocked = true,
                        floatingActionButtonPosition = FabPosition.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = "profile"
                            ) {
                                composable("messages") { Messages() }
                                composable("messaging") { Messaging() }
                                composable("feeds") { Feeds() }
                                composable("create") { Create() }
                                composable("contacts") { Contacts() }
                                composable("profile") { Profile() }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    public fun currentRoute(navController: NavHostController): String? {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
    }

}