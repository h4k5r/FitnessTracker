package io.dev00.fitnesstracker.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import io.dev00.fitnesstracker.components.*
import io.dev00.fitnesstracker.models.Goal
import io.dev00.fitnesstracker.models.Preference
import io.dev00.fitnesstracker.models.Steps
import io.dev00.fitnesstracker.navigation.FitnessTrackerScreens
import io.dev00.fitnesstracker.utils.fetchCurrentDate
import io.dev00.fitnesstracker.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)

    ) {
        TopBar(navController = navController) {
            Text(
                text = "Hello",
                modifier = Modifier,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight(300)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        TopCard(homeViewModel = homeViewModel)
        Spacer(modifier = Modifier.height(30.dp))
        BottomCard(homeViewModel = homeViewModel, navController = navController)
    }
}

@Composable
fun TopCard(homeViewModel: HomeViewModel) {

    val activeGoal = homeViewModel.activeGoal.collectAsState().value
    val currentSteps = homeViewModel.currentSteps.collectAsState().value[0].steps
    val isSameDate = fetchCurrentDate() == homeViewModel.getDate()

    BackgroundCard {
        Column(modifier = Modifier.padding(20.dp)) {
            if (activeGoal.isNullOrEmpty()) {
                Text(
                    text = "No Active Goal",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight(500)
                )

                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(text = "Number of Steps : ")
                    Text(text = buildAnnotatedString {
                        append(
                            AnnotatedString(
                                currentSteps.toString(),
                                spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                            )
                        )
                        append(
                            AnnotatedString(
                                text = " steps",
                            )
                        )
                    })
                }
            } else if (!activeGoal.isNullOrEmpty() && isSameDate) {
                Column() {
                    Text(
                        text = buildAnnotatedString {
                            append(
                                AnnotatedString(
                                    "Active Goal : ", spanStyle = SpanStyle(
                                        fontSize = MaterialTheme.typography.h5.fontSize,
                                        fontWeight = FontWeight(300)
                                    )
                                )
                            )
                            append(
                                AnnotatedString(
                                    activeGoal[0].goalName, spanStyle = SpanStyle(
                                        fontSize = MaterialTheme.typography.h5.fontSize,
                                        fontWeight = FontWeight(500)
                                    )
                                )
                            )
                        },
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Progress(currentSteps = currentSteps, activeGoal = activeGoal[0])
                }
            } else {
                var selectedDaySteps = homeViewModel.selectedDateSteps.collectAsState().value[0]
                var selectedDayGoal =
                    Goal(goalName = selectedDaySteps.goalName, steps = selectedDaySteps.target)
                var selectedDayGoalName by remember {
                    mutableStateOf(selectedDayGoal.goalName.trim())
                }
                if (selectedDayGoalName.isEmpty()) {
                    selectedDayGoalName = "No Active Goal"
                }
                if (selectedDayGoal.goalName.trim().isEmpty()) {
                    val historyGoal = homeViewModel.getHistoryGoal()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append(
                                    AnnotatedString(
                                        "Set Goal: ", spanStyle = SpanStyle(
                                            fontWeight = FontWeight(300),
                                            fontSize = MaterialTheme.typography.h5.fontSize
                                        )
                                    )
                                )
                            },
                        )
                        GoalsDropDown(
                            homeViewModel = homeViewModel,
                            selectedDayGoalName = selectedDayGoalName
                        ) {
                            homeViewModel.setHistoryGoal(it)
                            selectedDayGoalName = it.goalName
                            val tobeSaved = selectedDaySteps.copy()
                            tobeSaved.goalName = it.goalName
                            tobeSaved.target = it.steps
                            homeViewModel.insertHistorySteps(tobeSaved)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(text = "Number of Steps : ")
                        Text(text = buildAnnotatedString {
                            append(
                                AnnotatedString(
                                    selectedDaySteps.steps.toString(),
                                    spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                                )
                            )
                            append(
                                AnnotatedString(
                                    text = " steps",
                                )
                            )
                        })
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append(
                                    AnnotatedString(
                                        "Active Goal : ", spanStyle = SpanStyle(
                                            fontSize = MaterialTheme.typography.h5.fontSize,
                                            fontWeight = FontWeight(300)
                                        )
                                    )
                                )
                            },
                            fontSize = MaterialTheme.typography.h5.fontSize,
                            fontWeight = FontWeight(500)
                        )
                        GoalsDropDown(
                            homeViewModel = homeViewModel,
                            selectedDayGoalName = selectedDayGoalName
                        ) {
                            homeViewModel.setHistoryGoal(it)
                            selectedDayGoalName = it.goalName
                            val tobeSaved = selectedDaySteps.copy()
                            tobeSaved.goalName = it.goalName
                            tobeSaved.target = it.steps
                            homeViewModel.insertHistorySteps(tobeSaved)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Progress(
                        currentSteps = selectedDaySteps.steps,
                        activeGoal = homeViewModel.getHistoryGoal()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GoalsDropDown(
    homeViewModel: HomeViewModel,
    selectedDayGoalName: String,
    onGoalSelected: (goal: Goal) -> Unit
) {
    val selectedDaySteps = homeViewModel.selectedDateSteps.collectAsState().value[0]
    val selectedDayGoal =
        Goal(goalName = selectedDaySteps.goalName, steps = selectedDaySteps.target)
    val selectedDayGoalName by remember {
        mutableStateOf(selectedDayGoal.goalName.trim())
    }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchGoals = homeViewModel.searchGoals.collectAsState().value
    var selectedGoalDropDown by remember {
        mutableStateOf(false)
    }
    val expanded = remember {
        mutableStateOf(false)
    }
    val value = remember {
        mutableStateOf(selectedDayGoalName)
    }
    val coroutineScope = rememberCoroutineScope();
    val job = remember {
        mutableStateOf<Job>(Job())
    }
    val goalsDropDownItems: ArrayList<@Composable () -> Unit> = ArrayList()
    searchGoals.forEach {
        goalsDropDownItems.add {
            DropdownMenuItem(onClick = {
                value.value = it.goalName
                expanded.value = false
                onGoalSelected(it)
                focusManager.clearFocus()
            }) {
                Text(text = it.goalName)
            }
        }
    }
    AutoCompleteTextField(
        label = "Goal",
        fieldValue = value,
        expanded = expanded,
        composables = goalsDropDownItems,
        onSearch = {
            homeViewModel.searchGoals(
                it,
                successHandler = {
//                    job.value.cancel()
//                    job.value = coroutineScope.launch {
//                        delay(1200)
                    expanded.value = true
//                    }
                },
                failureHandler = {
                    expanded.value = false
                })
        },
        onDrop = {
            homeViewModel.searchGoals("")
        }
    )
}

@Composable
fun Progress(currentSteps: Int, activeGoal: Goal) {
    val progress: Float
    val target = activeGoal.steps
    if (target == 0) {
        progress = 0f
    } else if (currentSteps / target > 1) {
        progress = 1f
    } else {
        progress = currentSteps.toFloat() / target
    }
    val progressBarColor: Color
    if (progress >= 1f) {
        progressBarColor = Color.Green
    } else if (progress < 0.5f) {
        progressBarColor = Color.Red
    } else if (progress > 0.5f && progress < 1f) {
        progressBarColor = Color.Yellow
    } else {
        progressBarColor = Color.Transparent
    }
    Text(
        text = buildAnnotatedString {
            append(AnnotatedString("Progress : ", SpanStyle()))
            append(AnnotatedString((progress * 100).toInt().toString() + " %", SpanStyle()))
        },
        fontSize = MaterialTheme.typography.body1.fontSize,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = buildAnnotatedString {
            append(AnnotatedString("Walked : "))
            append(
                AnnotatedString(
                    currentSteps.toString(),
                    spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                )
            )
            append(
                AnnotatedString(
                    text = " steps",
                )
            )
        }, fontSize = 13.sp)
        Text(text = buildAnnotatedString {
            append(AnnotatedString("Target : "))
            append(
                AnnotatedString(
                    activeGoal.steps.toString(),
                    spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                )
            )
            append(
                AnnotatedString(
                    text = " steps",
                )
            )
        }, fontSize = 13.sp)
    }
    Spacer(modifier = Modifier.height(20.dp))
    BackgroundCard() {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            progress = progress,
            color = progressBarColor
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun BottomCard(homeViewModel: HomeViewModel, navController: NavController) {
    val preferencesList = homeViewModel.preferences.collectAsState().value
    val currentDaySteps: Steps = homeViewModel.currentSteps.collectAsState().value[0]
    val selectedDaySteps: Steps = homeViewModel.selectedDateSteps.collectAsState().value[0]
    val isSameDate = fetchCurrentDate() == homeViewModel.getDate()
    var date = homeViewModel.getDate()

    val context = LocalContext.current;

    var historicalEditing by remember {
        mutableStateOf(Preference(name = "historical editing", value = true))
    }
    val historical = preferencesList.find { it.name == "historical editing" }
    if (historical != null) {
        historicalEditing = historical
    }
    var isTouched by remember {
        mutableStateOf(false)
    }


    val datePickerDialog = configuredDatePickerDialog(context = context) {
        homeViewModel.setDate(it)
    }
    val stringSteps = remember {
        mutableStateOf("")
    }
    val isValid = stringSteps.value.trim().isNotEmpty() && stringSteps.value.isDigitsOnly()
    var parsedValue = 0
    if (isValid) {
        parsedValue = stringSteps.value.toInt()
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val Add = {
        val selectedDate = homeViewModel.getDate()
        if (selectedDate == fetchCurrentDate()) {
            currentDaySteps.steps += parsedValue
            homeViewModel.insertSteps(currentDaySteps)
            stringSteps.value = ""
            parsedValue = 0;
            focusManager.clearFocus()
            navController.backQueue.removeLast()
            navController.navigate(route = FitnessTrackerScreens.HomeScreen.name)
        } else {
            selectedDaySteps.steps += parsedValue
            if (isSameDate) {
                homeViewModel.insertSteps(selectedDaySteps)
            } else {
                homeViewModel.insertHistorySteps(steps = selectedDaySteps)
            }
            stringSteps.value = ""
            parsedValue = 0;
            focusManager.clearFocus()
            navController.backQueue.removeLast()
            navController.navigate(route = FitnessTrackerScreens.HomeScreen.name)
        }
    }
    BackgroundCard {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Add Steps",
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight(500)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Date : ")
                Text(text = date, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                if (historicalEditing.value) {
                    Button(
                        onClick = {
                            datePickerDialog.show()
                        },
                        modifier = Modifier.height(30.dp),
                        contentPadding = PaddingValues(top = 0.dp, bottom = 0.dp)
                    ) {
                        Text(text = "Change")
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedLeadingIconInputField(
                modifier = Modifier.fillMaxWidth(),
                valueState = stringSteps,
                label = "Steps",
                icon = Icons.Default.DirectionsWalk,
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!isValid) return@KeyboardActions
                    keyboardController?.hide()
                    Add()
                },
                onValueChange = {
                    isTouched = true
                },
            )
            if (!isValid && isTouched) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Enter Valid Number")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    Add()
                }, enabled = isValid
            ) {
                Text(text = "Add")
            }
        }
    }
}


