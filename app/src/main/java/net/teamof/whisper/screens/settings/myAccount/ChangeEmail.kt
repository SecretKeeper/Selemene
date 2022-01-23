package net.teamof.whisper.screens.settings.myAccount

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.teamof.whisper.R
import net.teamof.whisper.components.TextField
import net.teamof.whisper.ui.theme.fontFamily

@Composable
fun ChangeEmail() {

    val email = remember { mutableStateOf("vahid.security@gmail.com") }
    val emailError = remember { mutableStateOf(false) }

    val password = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf(false) }


    val buttonEnabled = remember { mutableStateOf(true) }
    val buttonText = remember { mutableStateOf("Change Email") }
    val buttonLoading = remember { mutableStateOf(false) }
    val buttonColor = remember { mutableStateOf(0xFF0336FF) }

    Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
        Text(
            text = "Change your email",
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
            text = "Email",
            value = email.value,
            onChange = {
                email.value = it.toString()
                emailError.value = it.toString().length < 3
            },
            isError = emailError.value,
            singleLine = true
        )

        TextField(
            text = "Current Password",
            value = password.value,
            onChange = {
                password.value = it.toString()
                passwordError.value = it.toString().length < 3
            },
            isError = passwordError.value,
            singleLine = true
        )

        Button(
            onClick = {

            },
            enabled = !passwordError.value || !emailError.value || buttonEnabled.value,
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