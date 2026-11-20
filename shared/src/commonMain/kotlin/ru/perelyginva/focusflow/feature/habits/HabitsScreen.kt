package ru.perelyginva.focusflow.feature.habits

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldValue
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HabitsScreen(
    viewModel: HabitsViewModel = koinViewModel<HabitsViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val adaptiveInfo = currentWindowAdaptiveInfoV2()
    val directive = calculatePaneScaffoldDirective(adaptiveInfo)
    val density = LocalDensity.current
    val windowWidthDp = with(density){
        LocalWindowInfo.current.containerSize.width.toDp()
    }
    val mediumBreakpoint = 600.dp
    val isWideScreen = windowWidthDp >= mediumBreakpoint

    val scaffoldValue by remember(isWideScreen){
        derivedStateOf {
            if (isWideScreen) {
                ThreePaneScaffoldValue(
                    primary = PaneAdaptedValue.Expanded,
                    secondary = PaneAdaptedValue.Expanded,
                    tertiary = PaneAdaptedValue.Hidden
                )
            } else {
                ThreePaneScaffoldValue(
                    primary = PaneAdaptedValue.Hidden,
                    secondary = PaneAdaptedValue.Expanded,
                    tertiary = PaneAdaptedValue.Hidden
                )
            }
        }
    }

    ListDetailPaneScaffold(
        directive = directive,
        value = scaffoldValue,
        listPane = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.statusBars) //Сначала учитываем системные бары (статус-бар + навбар)
                    .padding(16.dp)
            ) {
                Text(
                    "Список привычек",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.height(8.dp))

                when (val currentState = state) {

                    is HabitsState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is HabitsState.Error -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "❌ ${currentState.message}",
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = { viewModel.onIntent(HabitsIntent.LoadHabits) }) {
                                Text("Повторить")
                            }
                        }
                    }

                    is HabitsState.Success -> {
                        LazyColumn {
                            items(currentState.habits) { habit ->
                                ListItem(
                                    headlineContent = { Text(habit) },
                                    modifier = Modifier
                                        .clickable { /* TODO */ }
                                )
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        },
        detailPane = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Детали привычки")
            }
        }
    )
}