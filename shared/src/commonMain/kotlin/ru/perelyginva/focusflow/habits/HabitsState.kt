package ru.perelyginva.focusflow.habits

data class HabitsState(
    val isLoading: Boolean = false,
    val habits: List<String> = emptyList(),
    val error: String? = null
)
