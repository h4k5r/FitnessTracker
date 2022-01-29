package io.dev00.fitnesstracker.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
import io.dev00.fitnesstracker.components.BackgroundCard
import io.dev00.fitnesstracker.components.OutlinedIconInputField
import io.dev00.fitnesstracker.components.configuredDatePickerDialog
import io.dev00.fitnesstracker.models.Steps
import io.dev00.fitnesstracker.navigation.FitnessTrackerScreens
import io.dev00.fitnesstracker.utils.fetchCurrentDate
import io.dev00.fitnesstracker.viewModel.HomeViewModel

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
        Text(
            text = "Hello",
            modifier = Modifier,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight(300)
        )
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
            }
            if (activeGoal.isNotEmpty()) {
                val activeGoal = activeGoal[0]
                val target = activeGoal.steps
                var progress: Float = 0f
                if (currentSteps / target > 1) {
                    progress = 1f
                } else {
                    progress = currentSteps.toFloat() / target
                }
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
                                    activeGoal.goalName, spanStyle = SpanStyle(
                                        fontSize = MaterialTheme.typography.h5.fontSize,
                                        fontWeight = FontWeight(500)
                                    )
                                )
                            )
                        },
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Progress:",
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
                            progress = progress
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun BottomCard(homeViewModel: HomeViewModel, navController: NavController) {
    val currentDaySteps: Steps = homeViewModel.currentSteps.collectAsState().value[0]
    val selectedDaySteps: Steps = homeViewModel.selectedDateSteps.collectAsState().value[0]
    val context = LocalContext.current;
    var date = homeViewModel.getDate()
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
    Column {
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
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedIconInputField(
                    modifier = Modifier.fillMaxWidth(),
                    valueState = stringSteps,
                    label = "Steps",
                    icon = Icons.Default.DirectionsWalk,
                    enabled = true,
                    isSingleLine = true,
                    onAction = KeyboardActions {
                        if (!isValid) return@KeyboardActions
                        keyboardController?.hide()
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val selectedDate = homeViewModel.getDate()
                        if (selectedDate == fetchCurrentDate()) {
                            Log.d("TAG", "BottomCard: ${currentDaySteps.toString()}")
                            currentDaySteps.steps += parsedValue
                            homeViewModel.insertSteps(currentDaySteps)
                            stringSteps.value = ""
                            parsedValue = 0;
                            focusManager.clearFocus()
                            navController.backQueue.removeLast()
                            navController.navigate(route = FitnessTrackerScreens.HomeScreen.name)
                        } else {
                            selectedDaySteps.steps += parsedValue
                            homeViewModel.insertSteps(selectedDaySteps)
                            stringSteps.value = ""
                            parsedValue = 0;
                            focusManager.clearFocus()
                        }
                    }, enabled = isValid
                ) {
                    Text(text = "Add")
                }
            }
        }
    }
}


