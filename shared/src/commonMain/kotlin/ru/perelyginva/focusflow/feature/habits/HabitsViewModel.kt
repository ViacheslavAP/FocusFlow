package ru.perelyginva.focusflow.feature.habits

import androidx.lifecycle.ViewModel
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
) : ViewModel() {
    private val viewModelScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )

    private val _state = MutableStateFlow<HabitsState>(HabitsState.Loading)
    val state: StateFlow<HabitsState> = _state.asStateFlow()

    init {
        onIntent(HabitsIntent.LoadHabits)
    }

    fun onIntent(intent: HabitsIntent) {
        viewModelScope.launch {
            when (intent) {
                is HabitsIntent.LoadHabits -> {
                    _state.value = HabitsState.Loading
                    try {
                        val habits = habitsRepository.getAll()
                        _state.value = HabitsState.Success(habits)
                    } catch (e: Exception) {
                        _state.value = HabitsState.Error(
                            e.message ?: "Неизвестная ошибка"
                        )
                    }
                }

                is HabitsIntent.ToggleHabit -> {
                    try {
                        habitsRepository.toggle(intent.id)
                        onIntent(HabitsIntent.LoadHabits)
                    } catch (e: Exception) {
                        _state.value = HabitsState.Error(e.message ?: "Ошибка обновления")

                    }
                }
            }
        }
    }

    override fun onCleared() {
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