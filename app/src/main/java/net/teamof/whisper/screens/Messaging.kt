package net.teamof.whisper.screens

import BackPressHandler
import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import net.teamof.whisper.components.Message
import net.teamof.whisper.components.messaging.MessagingAttachSource
import net.teamof.whisper.components.messaging.MessagingFooter
import net.teamof.whisper.components.messaging.MessagingHeader
import net.teamof.whisper.models.Message
import net.teamof.whisper.viewModel.MessagesViewModel
import net.teamof.whisper.viewModel.MessagesViewModelFactory

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@SuppressLint("RememberReturnType")
@Composable
fun Messaging(
    navController: NavController,
    user_id: String,
    messagesViewModel: MessagesViewModel = viewModel(factory = MessagesViewModelFactory(1))
) {

    val messages: List<Message> by messagesViewModel.messages.observeAsState(listOf())
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val scope = rememberCoroutineScope()
    val selection = remember { mutableStateOf(false) }
    if (selection.value || bottomSheetState.isVisible) {
        BackPressHandler {
            selection.value = false
            scope.launch { bottomSheetState.hide() }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = { MessagingAttachSource() },
        sheetState = bottomSheetState
    ) {

        Column {
            MessagingHeader(navController, selection, user_id)
            Column(Modifier.weight(1f)) {
                Column(
                    Modifier.verticalScroll(
                        state = rememberScrollState(),
                        reverseScrolling = true
                    )
                ) {
                    messages.forEach { message ->
                        Message(
                            message,
                            selection.value,
                            enableSelectionMode = { selection.value = true },
                        )
                    }
                }
            }
            MessagingFooter(bottomSheetState, messagesViewModel)
        }
    }
}