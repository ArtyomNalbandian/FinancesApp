//package com.example.financesapp.presentation.history
//
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.res.painterResource
//import androidx.navigation.NavHostController
//import com.example.financesapp.R
//import com.example.financesapp.presentation.common.ScreenComponents
//import com.example.financesapp.ui.theme.Green
//
//object HistoryTopAppBarProvider : ScreenComponents {
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    override fun ProvideTopAppBar(navController: NavHostController) {
//        CenterAlignedTopAppBar(
//            title = { Text("История расходов") },
//            actions = {
//                IconButton(onClick = {
//                    navController.navigate("expenses_history_analysis")
//                }) {
//                    Icon(
//                        painter = painterResource(R.drawable.account),
//                        contentDescription = "Анализ расходов"
//                    )
//                }
//            },
//            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                containerColor = Green
//            )
//        )
//    }
//}
//
//object AnalysisTopAppBarProvider : ScreenComponents {
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    override fun ProvideTopAppBar(navController: NavHostController) {
//        CenterAlignedTopAppBar(
//            title = { Text("Анализ") },
//            actions = {
//                IconButton(onClick = {
//                    navController.navigate("expenses_history_analysis")
//                }) {
//                    Icon(
//                        painter = painterResource(R.drawable.account),
//                        contentDescription = "Анализ расходов"
//                    )
//                }
//            },
//            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                containerColor = Green
//            )
//        )
//    }
//}