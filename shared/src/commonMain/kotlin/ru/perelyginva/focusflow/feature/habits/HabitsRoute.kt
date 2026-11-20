package ru.perelyginva.focusflow.feature.habits

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HabitsRoute(){
    val viewModel: HabitsViewModel = koinViewModel()
    HabitsScreen(viewModel = viewModel)
}