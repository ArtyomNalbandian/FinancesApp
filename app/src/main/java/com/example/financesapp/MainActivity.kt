package com.example.financesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.financesapp.presentation.navigation.MainAppScreen
import com.example.financesapp.ui.theme.FinancesAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinancesAppTheme {
                MainAppScreen()
//                val navController = rememberNavController()
//                NavHost(
//                    navController = navController,
//                    startDestination = "splash"
//                ) {
//                    composable("splash") {
//                        SplashScreen {
//                            navController.navigate("main") {
//                                popUpTo("splash") { inclusive = true }
//                            }
//                        }
//                    }
//                    composable("main") { MainAppScreen() }
//                }
            }
        }
    }
}

@Composable
fun SplashScreen(onLoadingComplete: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.splash_screen)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(composition, progress)
    }

    LaunchedEffect(progress) {
        if (progress >= 0.93f) {
            onLoadingComplete()
        }
    }
}