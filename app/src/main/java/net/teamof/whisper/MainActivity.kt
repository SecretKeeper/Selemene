package net.teamof.whisper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.glide.GlideImage
import net.teamof.whisper.components.BottomNavigation
import net.teamof.whisper.components.floatingActionButton
import net.teamof.whisper.ui.theme.WhisperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            WhisperTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    
                    Scaffold(
                        bottomBar = {BottomNavigation()},
                        floatingActionButton = { floatingActionButton()},
                        isFloatingActionButtonDocked = true,
                        floatingActionButtonPosition = FabPosition.Center
                    ) {

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    GlideImage(
        imageModel = "https://openthread.google.cn/images/ot-contrib-google.png",
        // Crop, Fit, Inside, FillHeight, FillWidth, None
        contentScale = ContentScale.Crop,
        // shows an image with a circular revealed animation.
        circularRevealedEnabled = true
    )
}