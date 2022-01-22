package net.teamof.whisper.components.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
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
import kotlinx.coroutines.launch
import net.teamof.whisper.components.TextField
import net.teamof.whisper.ui.theme.fontFamily

@Composable
fun ChangeAccountPasswordScreen() {
    val composableScope = rememberCoroutineScope()
    val buttonEnabled = remember { mutableStateOf(true) }
    val buttonText = remember { mutableStateOf("Change Password") }
    val buttonLoading = remember { mutableStateOf(false) }
    val buttonColor = remember { mutableStateOf(0xFF0336FF) }
    val password = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf(false) }
    val newPassword = remember { mutableStateOf("") }
    val newPasswordError = remember { mutableStateOf(false) }
    val confirmPassword = remember { mutableStateOf("") }
    val confirmPasswordError = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
        TextField(
            text = "Current Password",
            value = password.value,
            onChange = {
                password.value = it.toString()
                passwordError.value = it.toString().length < 8
            },
            type = "password",
            isError = passwordError.value
        )
        TextField(
            text = "New Password",
            value = newPassword.value,
            onChange = {
                newPassword.value = it.toString()
                newPasswordError.value = it.toString().length < 8
            },
            type = "password",
            isError = newPasswordError.value
        )
        TextField(
            text = "Confirm Password",
            value = confirmPassword.value,
            onChange = {
                confirmPassword.value = it.toString()
                confirmPasswordError.value = it.toString().length < 8
            },
            type = "password",
            isError = confirmPasswordError.value
        )
        Button(
            onClick = {
                composableScope.launch {
//                    username.value.let {
//                        userViewModel.authenticate(
//                            navController,
//                            username.value,
//                            password.value,
//                            { buttonLoading.value = it },
//                            { buttonText.value = it },
//                            { buttonColor.value = it },
//                            { buttonEnabled.value = it }
//                        )
//                    }
                }
            },
            enabled = !passwordError.value || buttonEnabled.value,
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
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}