package com.example.desafio_mesas_comandas.view


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.desafio_mesas_comandas.ui.theme.AppNavigation

@Composable
fun MyApp() {
    val navController = rememberNavController()
    MaterialTheme {
        AppNavigation(navController = navController)
    }
}