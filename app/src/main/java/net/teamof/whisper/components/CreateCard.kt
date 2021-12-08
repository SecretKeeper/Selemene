package net.teamof.whisper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import net.teamof.whisper.screens.CreateCard
import net.teamof.whisper.ui.theme.fontFamily

@ExperimentalMaterialApi
@Composable
fun CreateCard(navController: NavController, createCard: CreateCard) {
    Card(
        elevation = 0.dp,
        onClick = { if (createCard.route.isNotEmpty()) navController.navigate("Contacts/Messaging") },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(bottom = 15.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 15.dp, horizontal = 20.dp)
        ) {
            Image(
                painter = painterResource(id = createCard.image),
                contentDescription = null,
                Modifier
                    .width(65.dp)
                    .height(65.dp)
                    .padding(end = 25.dp)
            )
            Column {
                Text(
                    text = createCard.title,
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = createCard.shortDescription,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    fontFamily = fontFamily,
                    color = Color.Gray
                )
            }
        }
    }
}