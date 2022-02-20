package io.dev00.fitnesstracker.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.dev00.fitnesstracker.components.*
import io.dev00.fitnesstracker.screens.*
import io.dev00.fitnesstracker.ui.theme.FitnessTrackerTheme
import io.dev00.fitnesstracker.viewModel.*

@ExperimentalComposeUiApi
@Composable
fun FitnessTrackerNavigation() {
    val navController = rememberNavController()
    val activeScreen = remember {
        mutableStateOf(FitnessTrackerScreens.HomeScreen.name)
    }

    // At the top level of your kotlin file:
    FitnessTrackerTheme {
        Surface(color = MaterialTheme.colors.background) {
            val goalsViewModel: GoalsViewModel = viewModel()
            val homeViewModel: HomeViewModel = viewModel()
            val historyViewModel: HistoryViewModel = viewModel()
            val editGoalViewModel: EditGoalViewModel = viewModel()
            val settingsViewModel: SettingsViewModel = viewModel()
            var modalConfig by remember {
                mutableStateOf(ModalConfiguration)
            }
            var snackBarConfig by remember {
                mutableStateOf(SnackBarConfig)
            }
            if (modalConfig.show.value) {
                YesOrNoModal(
                    title = modalConfig.title.value,
                    content = modalConfig.content.value,
                    onYesClickHandler = modalConfig.onYesClickHandler.value,
                    onNoClickHandler = modalConfig.onNoClickHandler.value
                )
            }
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, top = 30.dp, end = 20.dp),
                bottomBar = {
                    if (snackBarConfig.show.value) {
                        ConfiguredSnackBar(
                            content = snackBarConfig.content.value,
                            buttonText = snackBarConfig.buttonText.value,
                            buttonAction = snackBarConfig.buttonAction.value
                        )
                    }
                    BottomNavBar(
                        modifier = Modifier.fillMaxWidth(),
                        navController = navController,
                        activeScreen = activeScreen
                    )
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
                            goalsViewModel = goalsViewModel,
                        )
                    }
                    composable(route = FitnessTrackerScreens.HistoryScreen.name) {
                        activeScreen.value = FitnessTrackerScreens.HistoryScreen.name
                        HistoryScreen(
                            modifier = Modifier.padding(padding),
                            navController = navController,
                            historyViewModel = historyViewModel,
                            homeViewModel = homeViewModel
                        )
                    }
                    composable(route = FitnessTrackerScreens.AddGoalScreen.name) {
                        AddOrEditGoalScreen(
                            modifier = Modifier.padding(padding),
                            isAdd = true,
                            navController = navController,
                            goalsViewModel = goalsViewModel,
                            editGoalViewModel = editGoalViewModel
                        )
                    }
                    composable(
                        FitnessTrackerScreens.EditGoalScreen.name + "/{goalId}", arguments = listOf(
                            navArgument(name = "goalId") { type = NavType.StringType })
                    ) {
                        AddOrEditGoalScreen(
                            modifier = Modifier.padding(padding),
                            isAdd = false,
                            navController = navController,
                            goalsViewModel = goalsViewModel,
                            editGoalViewModel = editGoalViewModel,
                            goalId = it.arguments?.getString("goalId").toString().toInt()
                        )
                    }
                    composable(
                        route = FitnessTrackerScreens.SettingsScreen.name
                    ) {
                        SettingsScreen(
                            navController = navController,
                            settingsViewModel = settingsViewModel
                        )
                    }
                }
            }
        }
    }
}


