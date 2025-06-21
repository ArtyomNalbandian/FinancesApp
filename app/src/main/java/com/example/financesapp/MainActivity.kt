package com.example.financesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.financesapp.presentation.navigation.MainAppScreen
import com.example.financesapp.ui.theme.FinancesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinancesAppTheme {
//                var isSplashVisible by remember { mutableStateOf(true) }
//
//                if (isSplashVisible) {
//                    SplashScreen(
//                        onLoadingComplete = { isSplashVisible = false }
//                    )
//                } else {
                    MainAppScreen()
//                }
            }
        }
    }
}

@Composable
fun SplashScreen(onLoadingComplete: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))
    val progress by animateLottieCompositionAsState(composition, speed = 1.3f, iterations = 1)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(360.dp)
        )
    }

    LaunchedEffect(progress) {
        if (progress >= 1.0f) {
            onLoadingComplete()
        }
    }
}