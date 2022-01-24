package net.teamof.whisper.screens.settings.myAccount

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import net.teamof.whisper.R
import net.teamof.whisper.components.TextField
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.UserViewModel

@Composable
fun ChangePassword(navController: NavController, userViewModel: UserViewModel) {
    val composableScope = rememberCoroutineScope()

    val currentPassword = remember { mutableStateOf("") }
    val currentPasswordError = remember { mutableStateOf(false) }

    val newPassword = remember { mutableStateOf("") }
    val newPasswordError = remember { mutableStateOf(false) }

    val confirmPassword = remember { mutableStateOf("") }
    val confirmPasswordError = remember { mutableStateOf(false) }

    val buttonEnabled = remember { mutableStateOf(true) }
    val buttonText = remember { mutableStateOf("Change Password") }
    val buttonLoading = remember { mutableStateOf(false) }
    val buttonColor = remember { mutableStateOf(0xFF0336FF) }

    Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
        Text(
            text = "Change your password",
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 30.dp)
        )
//        Text(
//            text = "Change your email",
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.Bold,
//            fontSize = 16.sp,
//            color = Color.Gray,
//            modifier = Modifier.padding(bottom = 30.dp)
//        )

        TextField(
            type = "password",
            text = "Current Password",
            value = currentPassword.value,
            onChange = {
                currentPassword.value = it.toString()
                currentPasswordError.value = it.toString().length < 8
            },
            isError = currentPasswordError.value,
            singleLine = true
        )

        TextField(
            type = "password",
            text = "New Password",
            value = newPassword.value,
            onChange = {
                newPassword.value = it.toString()
                newPasswordError.value = it.toString().length < 8
            },
            isError = newPasswordError.value,
            singleLine = true
        )

        TextField(
            type = "password",
            text = "Confirm Password",
            value = confirmPassword.value,
            onChange = {
                confirmPassword.value = it.toString()
                confirmPasswordError.value = it.toString().length < 8
            },
            isError = confirmPasswordError.value,
            singleLine = true
        )
        if (newPassword.value.isNotEmpty() && confirmPassword.value.isNotEmpty()
            && newPassword.value != confirmPassword.value
        ) Text(
            text = "Password does not match",
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = Color.Red,
            modifier = Modifier.padding(bottom = 30.dp)
        )
        Button(
            onClick = {
                composableScope.launch {
                    userViewModel.changePassword(
                        navController,
                        currentPassword.value,
                        newPassword.value,
                        { buttonLoading.value = it },
                        { buttonText.value = it },
                        { buttonColor.value = it },
                        { buttonEnabled.value = it }
                    )
                }
            },
            enabled = !newPasswordError.value || !newPasswordError.value ||
                    !confirmPasswordError.value || buttonEnabled.value ||
                    newPassword.value != confirmPassword.value,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(buttonColor.value),
                disabledBackgroundColor = Color(buttonColor.value)
            ),
            shape = RoundedCornerShape(50)
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 5.dp)
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
                else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = buttonText.value,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_right_arrow),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        )
                    }
                }
            }
        }
    }
}