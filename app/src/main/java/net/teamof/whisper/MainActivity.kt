package net.teamof.whisper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.teamof.whisper.components.BottomAppBar
import net.teamof.whisper.components.FloatingActionButton
import net.teamof.whisper.screens.Contacts
import net.teamof.whisper.screens.Create
import net.teamof.whisper.screens.Feeds
import net.teamof.whisper.screens.Messages
import net.teamof.whisper.ui.theme.WhisperTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            WhisperTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        bottomBar = { BottomAppBar(navController) },
                        floatingActionButton = { FloatingActionButton()},
                        isFloatingActionButtonDocked = true,
                        floatingActionButtonPosition = FabPosition.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = "contacts"
                            ) {
                                composable("messages") { Messages() }
                                composable("feeds") { Feeds() }
                                composable("create") { Create()}
                                composable("contacts") { Contacts() }
                            }
                        }
                    }
                }
            }
        }
    }
}