package net.teamof.whisper.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import net.teamof.whisper.ui.theme.fontFamily

@Composable
fun TextField(text: String, value: String, setValue: (String) -> Unit, type: String = "text") {
    androidx.compose.material.TextField(
        label = { Text(text) },
        value = value,
        onValueChange = { setValue(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = when (type) {
                "text" -> KeyboardType.Text
                "password" -> KeyboardType.Text
                "email" -> KeyboardType.Email
                "integer" -> KeyboardType.Number
                else -> KeyboardType.Text
            }
        ),
        visualTransformation = if (type == "password") PasswordVisualTransformation() else VisualTransformation.None,
        textStyle = TextStyle(fontFamily = fontFamily),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(245, 245, 245),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp)
    )
}