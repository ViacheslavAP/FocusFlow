package ru.perelyginva.focusflow

import androidx.compose.runtime.Composable
import org.koin.dsl.module
import ru.perelyginva.focusflow.core.designsystem.FocusFlowTheme
import ru.perelyginva.focusflow.feature.habits.FakeHabitRepository
import ru.perelyginva.focusflow.feature.habits.HabitsScreen
import ru.perelyginva.focusflow.feature.habits.HabitsViewModel


val habitsModule = module {
    factory { HabitsViewModel(FakeHabitRepository()) }
}

@Composable
fun App() {

    org.koin.compose.KoinApplication(application = {
        modules(habitsModule)
        printLogger(org.koin.core.logger.Level.DEBUG)
    }) {
        FocusFlowTheme(darkTheme = isSystemInDarkTheme()) {
            HabitsScreen()
        }
    }
}