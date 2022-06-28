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
import net.teamof.whisper.viewModel.AuthViewModel

data class LoginButtonState(
    var text: String = "Login",
    var isLoading: Boolean = false,
    var btnColor: Long = 0xFF0336FF,
    var isEnabled: Boolean = true
)

@Composable()
fun LoginScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val composableScope = rememberCoroutineScope()
    val buttonState = remember { mutableStateOf(LoginButtonState()) }
    val username = remember { mutableStateOf("") }
    val usernameError = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf(false) }


    Column(Modifier.padding(horizontal = 25.dp, vertical = 15.dp)) {

        LoginHeader()

        Column(modifier = Modifier.weight(1f)) {
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
                            authViewModel.login(
                                navController,
                                username.value,
                                password.value,
                            ) { buttonState.value = it }
                        }
                    }
                },
                enabled = !passwordError.value || !usernameError.value || buttonState.value.isEnabled,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(buttonState.value.btnColor),
                    disabledBackgroundColor = Color(buttonState.value.btnColor)
                ),
                modifier = Modifier
                    .padding(vertical = 25.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                ) {
                    if (buttonState.value.isLoading)
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
                        text = buttonState.value.text,
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

        LoginFooter(navController)

    }
}


@Composable
fun LoginHeader() {
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
}


@Composable
fun LoginFooter(navController: NavController) {
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