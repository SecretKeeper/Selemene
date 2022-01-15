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
fun RegisterScreen(
    userViewModel: UserViewModel,
    navController: NavController
) {
    val composableScope = rememberCoroutineScope()
    val buttonEnabled = remember { mutableStateOf(true) }
    val buttonText = remember { mutableStateOf("Register") }
    val buttonLoading = remember { mutableStateOf(false) }
    val buttonColor = remember { mutableStateOf(0xFF0336FF) }
    val username = remember { mutableStateOf("") }
    val usernameError = remember { mutableStateOf(false) }
    val email = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf(false) }
    val cPassword = remember { mutableStateOf("") }
    val cPasswordError = remember { mutableStateOf(false) }


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
                text = "Email",
                value = email.value,
                onChange = {
                    email.value = it.toString()
                    emailError.value = it.toString().length < 3
                },
                isError = emailError.value
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
            TextField(
                text = "Confirm Password",
                value = password.value,
                onChange = {
                    cPassword.value = it.toString()
                    cPasswordError.value = it.toString().length < 8
                },
                type = "password",
                isError = cPasswordError.value
            )
            Button(
                onClick = {
                    composableScope.launch {
                        username.value.let {
                            userViewModel.signupWithEmailPassword(
                                navController,
                                username.value,
                                email.value,
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
                    )
                }
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
                text = "Already have account? Sign in",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth()
            )
        }
    }
}