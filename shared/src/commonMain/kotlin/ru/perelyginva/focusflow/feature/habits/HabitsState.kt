package ru.perelyginva.focusflow.feature.habits

sealed interface HabitsState {
    data object Loading : HabitsState
    data class Error(val message: String) : HabitsState
    data class Success(val habits: List<String>) : HabitsState
}
