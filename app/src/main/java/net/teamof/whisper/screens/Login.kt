package net.teamof.whisper.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
    val buttonEnabled = remember { mutableStateOf(true) }
    val buttonText = remember { mutableStateOf("Sign In") }
    val buttonLoading = remember { mutableStateOf(false) }
    val buttonColor = remember { mutableStateOf(0xFF0336FF) }
    val username = remember { mutableStateOf("") }
    val usernameError = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf(false) }


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
                onChange = {
                    username.value = it.toString()
                    usernameError.value = it.toString().length < 3
                },
                isError = usernameError.value
            )
            TextField(
                text = "Password",
                value = password.value,
                onChange = {
                    password.value = it.toString()
                    passwordError.value = it.toString().length < 8
                },
                type = "password",
                isError = passwordError.value
            )
            Button(
                onClick = {
                    composableScope.launch {
                        username.value.let {
                            userViewModel.authenticate(
                                navController,
                                username.value,
                                password.value,
                                { buttonLoading.value = it },
                                { buttonText.value = it },
                                { buttonColor.value = it },
                                { buttonEnabled.value = it }
                            )
                        }
                    }
                },
                enabled = !passwordError.value || !usernameError.value || buttonEnabled.value,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(buttonColor.value),
                    disabledBackgroundColor = Color(buttonColor.value)
                ),
                modifier = Modifier
                    .padding(vertical = 25.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                ) {
                    if (buttonLoading.value)
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        ) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.height(10.dp)
                            )
                        }
                    else Text(
                        text = buttonText.value,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
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
                    text = "Forgot Password?",
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                )
            }
        }

        TextButton(
            onClick = {
                navController.navigate("Register") {
                    launchSingleTop = true
                }
            }) {
            Text(
                text = "Does not have an account yet?",
                textAlign = TextAlign.Center,
                fontFamily = fontFamily,
                color = Color.Gray,
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth()
            )
        }
    }
}