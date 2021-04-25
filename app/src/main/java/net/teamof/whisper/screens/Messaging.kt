package net.teamof.whisper.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.teamof.whisper.R

@SuppressLint("RememberReturnType")
@Composable
fun Messaging() {

    val text = remember { mutableStateOf("") }


    Column {
        Column() {
            Text(text = "QWEQRT")
        }
        Column(Modifier.weight(1f)) {
            Text(text = "Hello Jaina")
        }
        Column(Modifier.background(Color(red = 245, green = 245, blue = 253))) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 15.dp, horizontal = 15.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    Modifier
                        .width(30.dp)
                        .height(30.dp)
                )
                OutlinedTextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp),
                    placeholder = {Text(text = "Write message here...")},
                    colors = TextFieldDefaults.textFieldColors (
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                            )

                )
            }
        }

    }
}