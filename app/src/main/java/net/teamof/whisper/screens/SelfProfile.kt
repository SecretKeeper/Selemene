package net.teamof.whisper.screens

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import net.teamof.whisper.components.conversation.Avatar
import net.teamof.whisper.components.settings.SettingsItem
import net.teamof.whisper.ui.theme.RedAlertColor
import net.teamof.whisper.ui.theme.fontFamily
import net.teamof.whisper.viewModel.UserViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SelfProfile(navController: NavController, userViewModel: UserViewModel) {

	val context = LocalContext.current
	val openDialog = remember { mutableStateOf(false) }

	val showToast = Toast.makeText(context, "This feature is not available yet", Toast.LENGTH_SHORT)
	val username = userViewModel.gePair("username")?.value ?: ""
	val avatar = remember { mutableStateOf(userViewModel.gePair("avatar")?.value ?: "") }

	var imageUri by remember {
		mutableStateOf<Uri?>(null)
	}

	val bitmap = remember {
		mutableStateOf<Bitmap?>(null)
	}

	val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
		if (result.isSuccessful) {
			imageUri = result.uriContent
			imageUri?.let { userViewModel.setAvatar(it) }
		} else {
			val exception = result.error
		}
	}

	val imagePickerLauncher =
		rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
			val cropOptions = CropImageContractOptions(
				uri,
				CropImageOptions()
			)
				.setImageSource(includeCamera = false, includeGallery = true)
				.setGuidelines(CropImageView.Guidelines.ON)
				.setCropCornerShape(cornerShape = CropImageView.CropCornerShape.RECTANGLE)
				.setAspectRatio(4, 5)

			imageCropLauncher.launch(
				cropOptions
			)
		}

	Column {
		Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier.padding(bottom = 15.dp)
			) {
				Avatar(
					if (imageUri != null)
						Uri.parse(imageUri.toString()).toString()
					else
						avatar.value,
					username,
					90,
					90
				) { openDialog.value = true }

				if (openDialog.value) {
					AlertDialog(
						onDismissRequest = {
							openDialog.value = false
						},
						title = {
							Text(text = "Avatar")
						},
						text = {
							Text(
								"which presents the details regarding the Dialog's purpose.",
								fontSize = 14.sp,
								fontFamily = fontFamily,
								fontWeight = FontWeight.Normal,
								lineHeight = 22.sp,
							)
						},
						confirmButton = {
							TextButton(
								onClick = {
									avatar.value = ""
									userViewModel.removeAvatar()
									openDialog.value = false
								}
							) {
								Text(
									"Remove",
									color = RedAlertColor,
									fontSize = 14.sp,
									fontFamily = fontFamily,
									fontWeight = FontWeight.SemiBold,
									letterSpacing = 0.sp
								)
							}
						},
						dismissButton = {
							TextButton(
								onClick = {
									imagePickerLauncher.launch("image/*")
									openDialog.value = false
								}
							) {
								Text(
									"Set Image",
									fontSize = 14.sp,
									fontFamily = fontFamily,
									fontWeight = FontWeight.SemiBold,
									letterSpacing = 0.sp
								)
							}
						}
					)
				}

				Column(modifier = Modifier.padding(start = 20.dp)) {
					Text(
						text = username,
						fontFamily = fontFamily,
						fontWeight = FontWeight.Bold,
						fontSize = 18.sp,
						modifier = Modifier.padding(bottom = 10.dp)
					)
					Text(
						text = userViewModel.gePair("status")?.value ?: "",
						fontFamily = fontFamily,
						fontWeight = FontWeight.Normal,
						fontSize = 16.sp,
						overflow = TextOverflow.Ellipsis,
						maxLines = 1
					)
				}
			}
		}

		Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
			SettingsItem(title = "My Account", event = { navController.navigate("MyAccount") })
		}

		Box(
			modifier = Modifier
				.fillMaxWidth()
				.background(Color(red = 235, green = 235, blue = 235))
				.height(1.dp)
		)

		Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
			SettingsItem(title = "Notifications", event = { showToast.show() })
			SettingsItem(title = "Privacy", event = { showToast.show() })
			SettingsItem(title = "Security", event = { navController.navigate("Security") })
			SettingsItem(title = "Devices", event = { showToast.show() })
		}

		Box(
			modifier = Modifier
				.fillMaxWidth()
				.background(Color(red = 235, green = 235, blue = 235))
				.height(1.dp)
		)

		Column(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
			SettingsItem(title = "FAQs", event = { showToast.show() })
			SettingsItem(title = "Privacy & Policy", event = { showToast.show() })
			SettingsItem(title = "Contact Support", event = { showToast.show() })
		}

		Box(
			modifier = Modifier
				.fillMaxWidth()
				.background(Color(red = 235, green = 235, blue = 235))
				.height(1.dp)
		)

		Column(modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
			Text(
				text = "Whispers - Version 1.0.0-alpha",
				fontFamily = fontFamily,
				fontWeight = FontWeight.Medium,
				fontSize = 14.sp,
				color = Color.Gray,
				modifier = Modifier.weight(1f)
			)
		}
	}
}