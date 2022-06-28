package net.teamof.whisper.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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

data class RegisterButtonState(
    var text: String = "Register",
    var isLoading: Boolean = false,
    var btnColor: Long = 0xFF0336FF,
    var isEnabled: Boolean = true
)

data class RegisterScreenState(
    var isRegistrationComplete: Boolean = false,
    var buttonState: RegisterButtonState
)

@ExperimentalAnimationApi
@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
) {
    val username = remember { mutableStateOf("") }
    val registerScreenState =
        remember { mutableStateOf(RegisterScreenState(buttonState = RegisterButtonState())) }

    Column(Modifier.padding(horizontal = 25.dp, vertical = 15.dp)) {
        AnimatedContent(
            targetState = registerScreenState.value.isRegistrationComplete,
            transitionSpec = {
                if (registerScreenState.value.isRegistrationComplete) {
                    slideInHorizontally(initialOffsetX = { height -> height }) + fadeIn() with
                            slideOutHorizontally(targetOffsetX = { height -> -height }) + fadeOut()
                } else {
                    slideInHorizontally(initialOffsetX = { height -> -height }) + fadeIn() with
                            slideOutHorizontally(targetOffsetX = { height -> height }) + fadeOut()
                }.using(
                    SizeTransform(clip = true)
                )
            }
        ) { showActions ->
            if (showActions)
                SigningAfterRegistration(
                    navController,
                    authViewModel,
                    registerScreenState.value,
                    username
                )
            else
                RegisterForm(authViewModel, registerScreenState, username)
        }
    }
}

@Composable
fun RegisterForm(
    authViewModel: AuthViewModel,
    registerScreenState: MutableState<RegisterScreenState>,
    username: MutableState<String>
) {
    val composableScope = rememberCoroutineScope()
    val usernameError = remember { mutableStateOf(false) }
    val email = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf(false) }
    val cPassword = remember { mutableStateOf("") }
    val cPasswordError = remember { mutableStateOf(false) }


    Column {
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
                value = cPassword.value,
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
                            authViewModel.signupWithEmailPassword(
                                username.value,
                                email.value,
                                password.value,
                            ) { registerScreenState.value = it }
                        }
                    }
                },
                enabled = !passwordError.value || !usernameError.value || registerScreenState.value.buttonState.isEnabled,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(registerScreenState.value.buttonState.btnColor),
                    disabledBackgroundColor = Color(registerScreenState.value.buttonState.btnColor)
                ),
                modifier = Modifier
                    .padding(vertical = 25.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                ) {
                    if (registerScreenState.value.buttonState.isLoading)
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
                        text = registerScreenState.value.buttonState.text,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = fontFamily,
                    )
                }
            }
        }
        TextButton(
            onClick = {
//                navController.navigate("Conversations") {
//                    launchSingleTop = true
//                    popUpTo("Login") { inclusive = true }
//                }
                registerScreenState.value.isRegistrationComplete = true
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

@Composable
fun SigningAfterRegistration(
    navController: NavController,
    authViewModel: AuthViewModel,
    registerScreenState: RegisterScreenState,
    username: MutableState<String>
) {

    val composableScope = rememberCoroutineScope()
    val loginButtonState = remember { mutableStateOf(LoginButtonState()) }
    val password = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf(false) }

    Column {
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
            Text(
                text = "Hey ${username.value} Enter your password to keep going",
                textAlign = TextAlign.Center,
                color = Color(97, 102, 125),
                fontFamily = fontFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 35.dp)
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
                                password.value
                            ) {
                                loginButtonState.value = it
                            }
                        }
                    }
                },
                enabled = !passwordError.value || loginButtonState.value.isEnabled,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(loginButtonState.value.btnColor),
                    disabledBackgroundColor = Color(loginButtonState.value.btnColor)
                ),
                modifier = Modifier
                    .padding(vertical = 25.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                ) {
                    if (loginButtonState.value.isLoading)
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
                        text = loginButtonState.value.text,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = fontFamily,
                    )
                }
            }
        }
        TextButton(
            onClick = {
//                navController.navigate("Conversations") {
//                    launchSingleTop = true
//                    popUpTo("Login") { inclusive = true }
//                }
                registerScreenState.isRegistrationComplete = false
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