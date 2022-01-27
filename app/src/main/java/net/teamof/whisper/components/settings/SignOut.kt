package net.teamof.whisper.components.settings


import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.UserViewModel

@Composable
fun SignOut(navController: NavController, userViewModel: UserViewModel) {
    val composableScope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }

    SettingsItem(
        title = "Sign Out",
        event = {
            openDialog.value = true
        },
        titleColor = Color.Red
    )

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    text = "Sign Out",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                )
            },
            text = {
                Text(
                    "This area typically contains the supportive text " +
                            "which presents the details regarding the Dialog's purpose.",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = Color.Gray,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        composableScope.launch {
                            openDialog.value = false
                            userViewModel.signOut(navController)
                        }
                    }
                ) {
                    Text(
                        "Confirm",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.Red,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text(
                        "Cancel",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                    )
                }
            }
        )
    }
}