package net.teamof.whisper.components.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import net.teamof.whisper.R
import net.teamof.whisper.ui.theme.fontFamily

@Composable
fun SettingTemplate(
    navController: NavController,
    title: String,
    children: @Composable() () -> Unit
) {
    Column(modifier = Modifier.padding(top = 30.dp, start = 5.dp, end = 5.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_left),
                    contentDescription = null,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
            }

            Text(
                text = title,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                modifier = Modifier
                    .weight(1f)
            )

            IconButton(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            ) {

            }
        }

        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
            children()
        }
    }
}