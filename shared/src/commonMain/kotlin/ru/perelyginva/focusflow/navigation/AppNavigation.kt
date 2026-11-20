package ru.perelyginva.focusflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import ru.perelyginva.focusflow.feature.habits.HabitsRoute

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.Habits
    ) {
        composable<AppRoute.Habits> {
            HabitsRoute()
        }
    }
}