package io.dev00.fitnesstracker.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.dev00.fitnesstracker.components.BottomNavBar
import io.dev00.fitnesstracker.screens.AddOrEditGoalScreen
import io.dev00.fitnesstracker.screens.GoalsScreen
import io.dev00.fitnesstracker.screens.HistoryScreen
import io.dev00.fitnesstracker.screens.HomeScreen
import io.dev00.fitnesstracker.ui.theme.FitnessTrackerTheme
import io.dev00.fitnesstracker.viewModel.GoalsViewModel
import io.dev00.fitnesstracker.viewModel.HistoryViewModel
import io.dev00.fitnesstracker.viewModel.HomeViewModel

@ExperimentalComposeUiApi
@Composable
fun FitnessTrackerNavigation() {
    val navController = rememberNavController();
    val activeScreen = remember {
        mutableStateOf(FitnessTrackerScreens.HomeScreen.name)
    }
    FitnessTrackerTheme {
        Surface(color = MaterialTheme.colors.background) {
            val goalsViewModel: GoalsViewModel = viewModel()
            val homeViewModel: HomeViewModel = viewModel()
            val historyViewModel:HistoryViewModel = viewModel()
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, top = 30.dp, end = 20.dp),
                bottomBar = {
                    BottomNavBar(modifier = Modifier.fillMaxWidth(), navController = navController, activeScreen = activeScreen)
                }
            ) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = FitnessTrackerScreens.HomeScreen.name
                ) {
                    composable(route = FitnessTrackerScreens.HomeScreen.name) {
                        activeScreen.value = FitnessTrackerScreens.HomeScreen.name
                        HomeScreen(
                            modifier = Modifier.padding(padding),
                            navController = navController,
                            homeViewModel = homeViewModel
                        )
                    }
                    composable(route = FitnessTrackerScreens.GoalsScreen.name) {
                        activeScreen.value = FitnessTrackerScreens.GoalsScreen.name
                        GoalsScreen(
                            modifier = Modifier.padding(padding),
                            navController = navController,
                            goalsViewModel = goalsViewModel
                        )
                    }
                    composable(route = FitnessTrackerScreens.HistoryScreen.name) {
                        activeScreen.value = FitnessTrackerScreens.HistoryScreen.name
                        HistoryScreen(
                            modifier = Modifier.padding(padding),
                            navController = navController,
                            historyViewModel = historyViewModel
                        )
                    }
                    composable(route = FitnessTrackerScreens.AddGoalScreen.name) {
                        AddOrEditGoalScreen(
                            modifier = Modifier.padding(padding),
                            isAdd = true,
                            navController = navController,
                            goalsViewModel = goalsViewModel
                        )
                    }
                }
            }
        }
    }
}