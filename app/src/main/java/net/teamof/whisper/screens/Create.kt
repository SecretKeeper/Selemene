package net.teamof.whisper.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.teamof.whisper.R
import net.teamof.whisper.components.CreateCard

sealed class CreateCard(
	val image: Int,
	val title: String,
	val shortDescription: String,
	val route: String
) {
	object Whisper : CreateCard(
		R.drawable.ic_flash,
		"Start Conversation",
		"Start chatting instantly with anybody using AES-256 encryption",
		"Contacts/Messaging"
	)

	object Feed : CreateCard(
		R.drawable.ic_timeline,
		"Create Feed",
		"Start chatting instantly with anybody using AES-256 encryption",
		""
	)

	object Group : CreateCard(
		R.drawable.ic_heart,
		"Create Group",
		"Start chatting instantly with anybody using AES-256 encryption",
		""
	)

	object Guild : CreateCard(
		R.drawable.ic_heart,
		"Create Guild",
		"Start chatting instantly with anybody using AES-256 encryption",
		""
	)
}

@ExperimentalMaterialApi
@Composable
fun Create(navController: NavController) {

	val items = listOf(
		CreateCard.Whisper,
		CreateCard.Feed,
		CreateCard.Group,
		CreateCard.Guild
	)

	Column(
		Modifier
			.background(Color.White)
			.padding(20.dp)
			.fillMaxSize()
	) {
		items.forEach { createCard ->
			CreateCard(navController, createCard)
		}
	}
}