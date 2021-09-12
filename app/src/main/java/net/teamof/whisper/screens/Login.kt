package net.teamof.whisper.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import net.teamof.whisper.viewModel.UserViewModel

@Composable()
fun LoginScreen(userViewModel: UserViewModel, navController: NavController) {

    val composableScope = rememberCoroutineScope()
    val setUserId = remember { mutableStateOf(0) }

    Column {
        Text(text = "It's Login Form")
        TextField(
            value = setUserId.value.toString(),
            onValueChange = { setUserId.value = it.toInt() },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )
        TextButton(onClick = {
            composableScope.launch { userViewModel.setUserID(setUserId.value.toLong()) }
            navController.navigate("Conversations")
        }) {
            Text(text = "Go to first destination")
        }
    }
}