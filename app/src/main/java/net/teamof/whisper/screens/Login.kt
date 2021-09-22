package net.teamof.whisper.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import net.teamof.whisper.components.TextField
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.UserViewModel

@Composable()
fun LoginScreen(
    userViewModel: UserViewModel,
    navController: NavController
) {
    val composableScope = rememberCoroutineScope()
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(Modifier.padding(horizontal = 25.dp, vertical = 15.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Welcome to Whispers!",
                textAlign = TextAlign.Center,
                fontFamily = fontFamily,
                fontSize = 26.sp,
                fontWeight = FontWeight(700),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            )
            Text(
                text = "Keep your data safe",
                textAlign = TextAlign.Center,
                color = Color(97, 102, 125),
                fontFamily = fontFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 35.dp)
            )
            TextField(
                text = "Username",
                value = username.value,
                setValue = { username.value = it }
            )
            TextField(
                text = "Password",
                value = password.value,
                setValue = { password.value = it },
                type = "password"
            )
            Button(
                onClick = {
                    composableScope.launch {
                        username.value.let {
                            userViewModel.authenticate(
                                username.value,
                                password.value
                            )
                        }
                    }
                    navController.navigate("Conversations") {
                        launchSingleTop = true
                        popUpTo("Login") { inclusive = true }
                    }
                }) {
                Text(
                    text = "Sign In",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily,
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .fillMaxWidth()
                )
            }
            TextButton(
                onClick = {
                    navController.navigate("Conversations") {
                        launchSingleTop = true
                        popUpTo("Login") { inclusive = true }
                    }
                }) {
                Text(
                    text = "Forgot Password?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .fillMaxWidth()
                )
            }
        }

        TextButton(
            onClick = {
                navController.navigate("Conversations") {
                    launchSingleTop = true
                    popUpTo("Login") { inclusive = true }
                }
            }) {
            Text(
                text = "Does not have an account yet?",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth()
            )
        }
    }
}