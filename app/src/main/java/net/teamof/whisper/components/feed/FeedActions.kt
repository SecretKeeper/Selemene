package net.teamof.whisper.components.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.teamof.whisper.R
import net.teamof.whisper.ui.theme.fontFamily

@Composable
fun FeedActions(
    showCommentsState: Boolean,
    showComments: () -> Unit,
    hideComments: () -> Unit,
    numberOfComments: Int
) {
    val actionIconSize = 20

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            onClick = if (showCommentsState) hideComments else showComments,
            enabled = numberOfComments > 0
        ) {
            Text(
                text = "${if (numberOfComments > 0) numberOfComments else "No"} Comments",
                fontSize = 13.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
            )
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