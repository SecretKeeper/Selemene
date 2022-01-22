package net.teamof.whisper.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.teamof.whisper.components.settings.SettingsItem

@Composable
fun SecurityScreen(navController: NavController) {
    Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
        SettingsItem(
            title = "Change Password Account"
        ) { navController.navigate("ChangeAccountPassword") }
        SettingsItem(title = "Password Lock")
        SettingsItem(title = "Fingerprint Unlock")
    }
}