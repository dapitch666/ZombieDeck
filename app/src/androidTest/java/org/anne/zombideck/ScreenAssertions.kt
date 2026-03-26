package org.anne.zombideck

import androidx.navigation.NavController
import org.junit.Assert

fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    val currentRoute = currentBackStackEntry?.destination?.route ?: ""
    Assert.assertTrue(
        "Expected route to end with $expectedRouteName but was $currentRoute",
        currentRoute.endsWith(expectedRouteName)
    )
}