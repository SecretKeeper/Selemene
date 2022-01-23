package net.teamof.whisper.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import net.teamof.whisper.components.settings.SettingsItem
import net.teamof.whisper.ui.theme.fontFamily

@Composable
fun MyAccount(navController: NavController) {

    Column {
        Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
            Text(
                text = "Account Information",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 30.dp)
            )
            SettingsItem(
                title = "Username",
                subTitle = "VahidSecurity",
                event = { navController.navigate("ChangeUsername") }
            )
            SettingsItem(
                title = "Email",
                subTitle = "vahid.security@gmail.com",
                event = { navController.navigate("ChangeEmail") }
            )
            SettingsItem(
                title = "Change Password",
                event = { navController.navigate("ChangePassword") }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(red = 235, green = 235, blue = 235))
                .height(1.dp)
        )

        Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
            SettingsItem(
                title = "Blocked Users",
                event = { navController.navigate("ChangeEmail") }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(red = 235, green = 235, blue = 235))
                .height(1.dp)
        )

        Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
            SettingsItem(
                title = "Deactivate Account",
                event = { navController.navigate("ChangeEmail") }
            )

            SettingsItem(
                title = "Delete Your Account",
                event = { navController.navigate("ChangeEmail") },
                titleColor = Color.Red
            )
        }
    }
}