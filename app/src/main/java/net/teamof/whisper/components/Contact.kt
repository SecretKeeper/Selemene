package net.teamof.whisper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import net.teamof.whisper.R
import net.teamof.whisper.models.UserAPI
import net.teamof.whisper.viewModel.ProfileViewModel

@OptIn(ExperimentalCoilApi::class)
@ExperimentalMaterialApi
@Composable
fun Contact(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    data: UserAPI,
    action: String
) {

    var checked by rememberSaveable { mutableStateOf(false) }

    Card(
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp),
        onClick = {
            when (action) {
                "CreateGroup" -> checked = !checked
                "Messaging" -> {
                    profileViewModel.getUserByUserID(
                        data.user_id
                    ) { navController.navigate("Profile/${data.user_id}") }
                }
            }
        }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = data.avatar,
                    builder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
            )
            Column(
                Modifier
                    .weight(2f)
                    .padding(start = 15.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = data.username,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(bottom = 7.dp)
                            .weight(1f)
                    )
                    if (action == "Messaging")
                        if (checked) Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_checkmark),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .padding(end = 10.dp)
                        )
                }
                Text(text = data.profile.status, fontSize = 13.sp)
            }
        }
    }
}