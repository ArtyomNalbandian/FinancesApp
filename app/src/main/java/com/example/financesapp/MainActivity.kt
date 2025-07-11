package com.example.financesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.financesapp.presentation.navigation.MainAppScreen
import com.example.financesapp.ui.theme.FinancesAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as FinancesApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinancesAppTheme {
                MainAppScreen(viewModelFactory = viewModelFactory)
            }
        }
    }
}
