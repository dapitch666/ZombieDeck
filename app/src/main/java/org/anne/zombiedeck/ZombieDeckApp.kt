package org.anne.zombiedeck

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
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
    playAbominationSound: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    ZombieDeckNavHost(
        playAbominationSound = playAbominationSound,
        navController = navController)
}

@Composable
fun ZombieDeckNavHost(
    playAbominationSound: () -> Unit,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "Welcome"
    ) {
        composable(
            route = "Welcome",
            exitTransition = {
                fadeOut(
                    animationSpec = tween(700, delayMillis = 700)
                )
            },
            popEnterTransition = { EnterTransition.None }
            ) {
            WelcomeScreen(
                navigateToDraw = { navController.navigate("Draw") },
                navigateToSettings = { navController.navigate("Settings") },
            )
        }
        composable(
            route = "Draw",
        ) {
            DrawScreen(
                playAbominationSound = playAbominationSound,
            )
        }
        composable(
            route = "Settings",
            enterTransition = {

                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700, delayMillis = 100)
                        )

            },
            popExitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700, delayMillis = 100)
                        )
            }
        ) {
            SettingsScreen(
                navigateUp = { navController.popBackStack() }
            )
        }
    }
}

