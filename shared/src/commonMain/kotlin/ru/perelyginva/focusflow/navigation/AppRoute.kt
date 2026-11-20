package ru.perelyginva.focusflow.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppRoute {
de
    @Serializable
    data object Habits: AppRoute()

}