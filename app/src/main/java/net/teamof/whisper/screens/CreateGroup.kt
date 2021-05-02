package net.teamof.whisper.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import net.teamof.whisper.R

@Composable
fun CreateGroup(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp, vertical = 75.dp)
    ) {
        OutlinedButton(
            onClick = { /*TODO*/ },
            shape = CircleShape,
            contentPadding = PaddingValues(30.dp),
            modifier = Modifier.padding(bottom = 25.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_image),
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = "Group Name",
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            TextField(
                value = "",
                onValueChange = { /*...*/ },
                shape = RoundedCornerShape(3.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier
                    .border(1.dp, Color.LightGray)
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = "Description",
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            TextField(
                value = "",
                onValueChange = { /*...*/ },
                shape = RoundedCornerShape(3.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier
                    .border(1.dp, Color.LightGray)
                    .fillMaxWidth()
            )
        }
        Row(modifier = Modifier.padding(vertical = 30.dp)) {
            Text(
                text = "Private",
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .weight(1f)
            )
            Switch(
                checked = true,
                onCheckedChange = { },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colors.primary
                )
            )
        }
        Button(
            onClick = { navController.navigate("contacts") },
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
            shape = RoundedCornerShape(3.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
        ) {
            Text(
                text = "Proceed to next step!",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 7.dp)
            )
        }
    }
}