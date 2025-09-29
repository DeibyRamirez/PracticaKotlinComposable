package com.cheiviz.practicacomposer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cheiviz.practicacomposer.screens.HomeScreen
import com.cheiviz.practicacomposer.screens.LoginScreen
import com.cheiviz.practicacomposer.screens.components.TaskDataStore


class MainActivity : ComponentActivity() {

    private val taskDataStore by lazy { TaskDataStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavHost( taskDataStore )
            }
        }

    }
}

// Navegacion entre pantallas, funciona de la siguiente manera.

// 1. Crear las pantallas que se van a navegar (Screens)
// 2. Crear el NavHost que se encarga de la navegacion (navController)
// 3. Crear los composables que se van a navegar (HomeScreen, LoginScreen)
// 4. Crear las acciones de navegacion en los composables (onLogin, onLogout)
@Composable
fun AppNavHost(taskDataStore: TaskDataStore) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            LoginScreen( onLogin = { name ->
                navController.navigate("home/$name")
            })
        }
        composable("home/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            HomeScreen(
                userName = username,
                taskDataStore = taskDataStore,
                onLogout = {
                    navController.popBackStack(
                        route = "login", inclusive = false
                    )
                    navController.navigate("login")
                }

            )
        }

    }

}



