package com.example.desafio_mesas_comandas.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.desafio_mesas_comandas.view.ConfigurationPage
import com.example.desafio_mesas_comandas.view.HomePage
import com.example.desafio_mesas_comandas.view.MapScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "HomePage"
    ) {
        composable("HomePage") {
            HomePage(navController = navController)
        }
        composable("MapScreen") {
            MapScreen(navController = navController)
        }
        composable("ConfigurationPage") {
            ConfigurationPage(navController = navController)
        }
    }
}
