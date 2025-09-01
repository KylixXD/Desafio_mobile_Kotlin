package com.example.desafio_mesas_comandas.view

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Balcao(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold (modifier = Modifier){
        Text("Balcao tela")
    }
}