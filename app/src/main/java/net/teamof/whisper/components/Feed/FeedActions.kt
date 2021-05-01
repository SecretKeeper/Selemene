package net.teamof.whisper.components.Feed

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import net.teamof.whisper.R

@Composable
fun FeedActions() {

    val actionIconSize = 20

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Comments")
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.weight(1f)
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_heart),
                    contentDescription = null,
                    Modifier
                        .width(actionIconSize.dp)
                        .height(actionIconSize.dp)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_share),
                    contentDescription = null,
                    Modifier
                        .width(actionIconSize.dp)
                        .height(actionIconSize.dp)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    Modifier
                        .width(actionIconSize.dp)
                        .height(actionIconSize.dp)
                )
            }
        }
    }
}