package io.dev00.fitnesstracker.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.dev00.fitnesstracker.components.BottomNavBar
import io.dev00.fitnesstracker.screens.AddOrEditGoalScreen
import io.dev00.fitnesstracker.screens.GoalsScreen
import io.dev00.fitnesstracker.screens.HistoryScreen
import io.dev00.fitnesstracker.screens.HomeScreen
import io.dev00.fitnesstracker.ui.theme.FitnessTrackerTheme

@ExperimentalComposeUiApi
@Composable
fun FitnessTrackerNavigation() {
    val navController = rememberNavController();
    FitnessTrackerTheme {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                modifier = Modifier.fillMaxSize().padding(start = 20.dp, top = 30.dp, end = 20.dp),
                bottomBar = {
                    BottomNavBar(modifier = Modifier.fillMaxWidth(), navController = navController)
                }
            ) { padding ->
//                AddOrEditGoalScreen(modifier = Modifier.padding(padding), isAdd = false)
                NavHost(
                    navController = navController,
                    startDestination = FitnessTrackerScreens.HomeScreen.name
                ) {
                    composable(route = FitnessTrackerScreens.HomeScreen.name) {
                        HomeScreen(modifier = Modifier.padding(padding), navController = navController)
                    }
                    composable(route = FitnessTrackerScreens.GoalsScreen.name) {
                        GoalsScreen(modifier = Modifier.padding(padding), navController = navController)
                    }
                    composable(route = FitnessTrackerScreens.HistoryScreen.name) {
                        HistoryScreen(modifier = Modifier.padding(padding), navController = navController)
                    }
                }
            }
        }
    }

}