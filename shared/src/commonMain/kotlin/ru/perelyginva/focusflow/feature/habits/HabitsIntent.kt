package ru.perelyginva.focusflow.feature.habits

interface HabitsIntent {
    data object LoadHabits : HabitsIntent
    data class ToggleHabit(val id: String) : HabitsIntent
}