package com.example.desafio_mesas_comandas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.desafio_mesas_comandas.ui.theme.Desafio_mesas_comandasTheme
import com.example.desafio_mesas_comandas.view.Balcao
import com.example.desafio_mesas_comandas.view.HomePage
import com.example.desafio_mesas_comandas.view.MapaAtendimento
import com.example.desafio_mesas_comandas.view.MesaComanda


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Desafio_mesas_comandasTheme (dynamicColor = false){
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "telaInicial"){
                    composable(
                        route = "telaInicial"
                    ){
                        HomePage(navController = navController)
                    }

                    composable(
                        route = "mapaAtendimento"
                    ){
                        MapaAtendimento(navController = navController)
                    }

                    composable(
                        route = "mesaComanda"
                    ){
                        MesaComanda(navController = navController)
                    }

                    composable(
                        route = "balcaoComanda"
                    ){
                        Balcao(navController = navController)
                    }

                }
            }

        }
    }
}



