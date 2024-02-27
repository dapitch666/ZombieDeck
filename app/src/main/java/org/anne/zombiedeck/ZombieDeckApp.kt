package org.anne.zombiedeck

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.anne.zombiedeck.settings.SettingsScreen
import org.anne.zombiedeck.ui.DrawScreen
import org.anne.zombiedeck.ui.WelcomeScreen

@Composable
fun ZombieDeckApp(
    navController: NavHostController = rememberNavController()
) {
    ZombieDeckNavHost(navController = navController)
}

@Composable
fun ZombieDeckNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "Welcome"
    ) {
        composable(route = "Welcome") {
            WelcomeScreen(
                navigateToDraw = { navController.navigate("Draw") },
                navigateToSettings = { navController.navigate("Settings") }
            )
        }
        composable(route = "Draw") {
            DrawScreen()
        }
        composable(route = "Settings") {
            SettingsScreen()
        }
    }
}

