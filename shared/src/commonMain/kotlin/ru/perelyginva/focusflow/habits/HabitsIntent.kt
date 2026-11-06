package ru.perelyginva.focusflow.habits

interface HabitsIntent {
    data object LoadHabits : HabitsIntent
    data class ToggleHabit(val id: String) : HabitsIntent
}