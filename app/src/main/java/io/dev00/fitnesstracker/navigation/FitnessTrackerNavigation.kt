package io.dev00.fitnesstracker.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.dev00.fitnesstracker.components.BackgroundCard
import io.dev00.fitnesstracker.components.BottomNavBar
import io.dev00.fitnesstracker.screens.AddOrEditGoalScreen
import io.dev00.fitnesstracker.screens.GoalsScreen
import io.dev00.fitnesstracker.screens.HistoryScreen
import io.dev00.fitnesstracker.screens.HomeScreen
import io.dev00.fitnesstracker.ui.theme.FitnessTrackerTheme
import io.dev00.fitnesstracker.viewModel.EditGoalViewModel
import io.dev00.fitnesstracker.viewModel.GoalsViewModel
import io.dev00.fitnesstracker.viewModel.HistoryViewModel
import io.dev00.fitnesstracker.viewModel.HomeViewModel

@ExperimentalComposeUiApi
@Composable
fun FitnessTrackerNavigation() {
    val navController = rememberNavController()
    val activeScreen = remember {
        mutableStateOf(FitnessTrackerScreens.HomeScreen.name)
    }
    FitnessTrackerTheme {
        Surface(color = MaterialTheme.colors.background) {
            val goalsViewModel: GoalsViewModel = viewModel()
            val homeViewModel: HomeViewModel = viewModel()
            val historyViewModel: HistoryViewModel = viewModel()
            val editGoalViewModel: EditGoalViewModel = viewModel()
            var modalConfig by remember {
                mutableStateOf(ModalConfiguration)
            }
            if(modalConfig.show.value) {
                YesOrNoModal(
                    title = modalConfig.title.value,
                    content = modalConfig.content.value,
                    onYesClickHandler = modalConfig.onYesClickHandler.value,
                    onNoClickHandler = modalConfig.onNoClickHandler.value)
            }
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, top = 30.dp, end = 20.dp),
                bottomBar = {
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
//                        DetailsScreen(navController = navController, id = it.arguments?.getString("movieId").toString() )
                    }
                }
            }
        }
    }
}

@Composable
fun YesOrNoModal(title:String,content: String, onYesClickHandler: () -> Unit, onNoClickHandler: () -> Unit) {
    Column(
        modifier = Modifier
            .background(Color(0x80000000))
            .fillMaxWidth()
            .fillMaxHeight()
            .zIndex(1000f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackgroundCard(modifier = Modifier.fillMaxWidth(0.9f)) {
            Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text( text = title, textAlign = TextAlign.Center, fontSize = MaterialTheme.typography.h5.fontSize)
                Spacer(modifier = Modifier.height(10.dp))
                Text( text = content, textAlign = TextAlign.Center)
                Row(modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = { onYesClickHandler() }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)) {
                        Text(text = "Yes")
                    }
                    Button(onClick = { onNoClickHandler() }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondaryVariant)) {
                        Text(text = "No")
                    }
                }
            }
        }
    }
}

object ModalConfiguration {
    var title = mutableStateOf("")
    var content = mutableStateOf("")
    var onYesClickHandler = mutableStateOf({})
    var onNoClickHandler = mutableStateOf({})
    var show = mutableStateOf(false)

    fun setModalConfig(title:String,content: String,onYesClickHandler: () -> Unit,onNoClickHandler: () -> Unit,show:Boolean) {
        this.show.value = show
        this.title.value = title
        this.content.value = content
        this.onNoClickHandler.value = onNoClickHandler
        this.onYesClickHandler.value = onYesClickHandler
    }
    fun clearModalConfig() {
        this.show.value = false
        this.title.value = ""
        this.content.value = ""
        this.onNoClickHandler.value = {}
        this.onYesClickHandler.value = {}
    }
}
