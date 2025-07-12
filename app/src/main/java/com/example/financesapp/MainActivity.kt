package com.example.financesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.financesapp.presentation.navigation.MainAppScreen
import com.example.financesapp.ui.theme.FinancesAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinancesAppTheme {
                MainAppScreen()
            }
        }
    }
}
