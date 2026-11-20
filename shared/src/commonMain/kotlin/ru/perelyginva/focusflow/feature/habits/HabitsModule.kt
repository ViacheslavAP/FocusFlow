package ru.perelyginva.focusflow.feature.habits

import org.koin.dsl.module

val habitsModule = module {
    factory {
        HabitsViewModel(get())
    }
    single<HabitsRepository> { FakeHabitRepository() }
}