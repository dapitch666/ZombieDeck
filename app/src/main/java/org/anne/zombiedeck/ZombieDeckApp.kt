package org.anne.zombiedeck

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chillibits.simplesettings.core.SimpleSettings
import org.anne.zombiedeck.ui.DrawScreen
import org.anne.zombiedeck.ui.WelcomeScreen

@Composable
fun ZombieDeckApp(
    context: Context,
    navController: NavHostController = rememberNavController()
) {
    ZombieDeckNavHost(context = context, navController = navController)
}

@Composable
fun ZombieDeckNavHost(
    context: Context,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "Welcome"
    ) {
        composable(route = "Welcome") {
            WelcomeScreen(
                navigateToDraw = { navController.navigate("Draw") },
                navigateToSettings = { SimpleSettings(context).show(R.xml.preferences) }
            )
        }
        composable(route = "Draw") {
            DrawScreen()
        }
    }
}

