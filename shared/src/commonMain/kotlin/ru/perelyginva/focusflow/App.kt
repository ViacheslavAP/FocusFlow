package ru.perelyginva.focusflow

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import ru.perelyginva.focusflow.core.designsystem.FocusFlowTheme
import ru.perelyginva.focusflow.feature.habits.habitsModule
import ru.perelyginva.focusflow.navigation.AppNavigation


@Composable
fun App() {

    KoinApplication(application = {
        modules(habitsModule)
        printLogger(org.koin.core.logger.Level.DEBUG)
    }) {
        FocusFlowTheme(darkTheme = isSystemInDarkTheme()) {
            AppNavigation()
        }
    }
}