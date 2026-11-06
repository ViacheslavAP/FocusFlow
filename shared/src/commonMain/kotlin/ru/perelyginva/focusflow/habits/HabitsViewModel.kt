package ru.perelyginva.focusflow.habits

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HabitsViewModel(
    private val habitsRepository: HabitsRepository = FakeHabitRepository()
) {
    private val viewModelScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )

    private val _state = MutableStateFlow(HabitsState())
    val state: StateFlow<HabitsState> = _state.asStateFlow()

    init {
       onIntent(HabitsIntent.LoadHabits)
    }

    fun onIntent(intent: HabitsIntent) {
        viewModelScope.launch {
            when (intent) {
                is HabitsIntent.LoadHabits -> {
                    _state.update { it.copy(isLoading = true) }
                    try {
                        val habits = habitsRepository.getAll()
                        _state.update { it.copy(isLoading = false, habits = habits) }
                    } catch (e: Exception) {
                        _state.update { it.copy(isLoading = false, error = e.message) }
                    }
                }
                is HabitsIntent.ToggleHabit -> {
                    habitsRepository.toggle(intent.id)
                    onIntent(HabitsIntent.LoadHabits)
                }
            }
        }
    }

    fun onCleared() {
        viewModelScope.cancel()
    }
}

interface HabitsRepository {
    suspend fun getAll(): List<String>
    suspend fun toggle(id: String)
}

class FakeHabitRepository : HabitsRepository {
    override suspend fun getAll() = listOf("Drink water", "Read 30 min")
    override suspend fun toggle(id: String) {}
}