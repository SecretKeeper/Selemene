package net.teamof.whisper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.teamof.whisper.R
import net.teamof.whisper.ui.theme.fontFamily

@Composable
fun UnderConstruction(percentFinished: Float) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.under_cons),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Creating...",
            fontFamily = fontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth()
        )

        Text(
            text = "We're trying to create this page as soon as possible.",
            fontFamily = fontFamily,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 35.dp)
                .fillMaxWidth()
        )

        LinearProgressIndicator(progress = percentFinished)

        Text(
            text = "${(percentFinished * 100).toInt()}% Done, ${(100 - percentFinished * 100).toInt()}% to Go!",
            fontFamily = fontFamily,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xffffff
)
@Composable
fun PrevUnderConstruction() {
    UnderConstruction(0.25f)
}