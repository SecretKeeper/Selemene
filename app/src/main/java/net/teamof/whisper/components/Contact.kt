package net.teamof.whisper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.rememberCoilPainter
import net.teamof.whisper.R
import net.teamof.whisper.models.Contact
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.viewModel.ConversationsViewModel
import net.teamof.whisper.viewModel.UserViewModel
import java.util.*

@ExperimentalMaterialApi
@Composable
fun Contact(
    navController: NavController,
    data: Contact,
    action: String,
    userViewModel: UserViewModel = viewModel(),
    conversationsViewModel: ConversationsViewModel = viewModel()
) {

    var checked by rememberSaveable(data.selected) { mutableStateOf(data.selected) }
    val currentUserId = userViewModel.userId.observeAsState()

    Card(
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp),
        onClick = {
            when (action) {
                "CreateGroup" -> checked = !checked
                "Messaging" -> {
                    val channelUUID: String = UUID.nameUUIDFromBytes(
                        (currentUserId.value.toString() + data.user_id).toByteArray()
                    ).toString()

                    conversationsViewModel.createConversation(
                        Conversation(
                            channel = channelUUID,
                            user_id = data.user_id,
                            username = data.username,
                            user_image = data.user_image,
                        )
                    )

                    navController.navigate("Messaging/$channelUUID")
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
                painter = rememberCoilPainter(request = data.user_image,
                    requestBuilder = {
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
                Text(text = data.status, fontSize = 13.sp)
            }
        }
    }
}