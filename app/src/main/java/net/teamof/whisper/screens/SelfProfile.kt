package net.teamof.whisper.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import net.teamof.whisper.components.settings.SettingsItem
import net.teamof.whisper.ui.theme.fontFamily

@Composable
fun SelfProfile(navController: NavController) {
    Column {


        Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 35.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = "https://uupload.ir/files/2fk9_6087f98a3cd34_(2).jpg",
                        builder = {
                            transformations(CircleCropTransformation())
                        }),
                    contentDescription = null,
                    modifier = Modifier
                        .width(90.dp)
                        .height(90.dp)

                )
                Column(modifier = Modifier.padding(start = 20.dp)) {
                    Text(
                        text = "Vahid Security",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "Non-can scape my fury",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                    )
                }
            }
            SettingsItem(title = "Notifications")
            SettingsItem(title = "Privacy")
            SettingsItem(title = "Security") { navController.navigate("Security") }
            SettingsItem(title = "Devices")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(red = 235, green = 235, blue = 235))
                .height(1.dp)
        )

        Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
            SettingsItem(title = "FAQs")
            SettingsItem(title = "Privacy & Policy")
            SettingsItem(title = "Contact Support")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(red = 235, green = 235, blue = 235))
                .height(1.dp)
        )

        Column(modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
            Text(
                text = "Whispers - Version 1.0.0-alpha",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
        }


    }
}