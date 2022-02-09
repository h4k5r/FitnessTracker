package io.dev00.fitnesstracker.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import io.dev00.fitnesstracker.components.BackgroundCard
import io.dev00.fitnesstracker.components.FilledCircularIconButton
import io.dev00.fitnesstracker.components.OutlinedInputField
import io.dev00.fitnesstracker.components.SnackBarConfig
import io.dev00.fitnesstracker.models.Goal
import io.dev00.fitnesstracker.navigation.FitnessTrackerScreens
import io.dev00.fitnesstracker.viewModel.EditGoalViewModel
import io.dev00.fitnesstracker.viewModel.GoalsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun AddOrEditGoalScreen(
    modifier: Modifier = Modifier,
    isAdd: Boolean = true,
    goalId: Int = 0,
    navController: NavController,
    goalsViewModel: GoalsViewModel,
    editGoalViewModel: EditGoalViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    var isInitial by remember {
        mutableStateOf(true)
    }

    var goalName = remember {
        mutableStateOf("")
    }
    val isNamevalid = goalName.value.trim().isNotEmpty()
    var isNameTouched by remember {
        mutableStateOf(false)
    }

    var goalSteps = remember {
        mutableStateOf("")
    }
    val isStepsValid = goalSteps.value.trim().isNotEmpty() && goalSteps.value.isDigitsOnly()
    var isStepsTouched by remember {
        mutableStateOf(false)
    }
    var isbuttonEnabled = isNamevalid && isStepsValid


    var toBeSavedGoal: Goal = Goal()
    if (!isAdd) {
        toBeSavedGoal = editGoalViewModel.getGoalById(id = goalId)
    }
    if (isInitial && !isAdd) {

        isInitial = false
        isbuttonEnabled = false
        goalName.value = toBeSavedGoal.goalName
        goalSteps.value = toBeSavedGoal.steps.toString()
    }


    var screenHeading: String

    var buttonText: String

    var buttonAction = {}
    if (isAdd) {
        screenHeading = "Create Goal"
        buttonText = "Add"
        buttonAction = {
            if (isNamevalid && isStepsValid) {
                keyboardController?.hide()
                goalsViewModel.addGoal(
                    goal = Goal(
                        goalName = goalName.value,
                        steps = goalSteps.value.toInt()
                    ),
                    failureCallback = {
                        SnackBarConfig.setSnackBarConfig(show = true, content = "Goal Already Exists", showButton = false)
                        coroutineScope.launch(Dispatchers.IO) {
                            Thread().run {
                                Thread.sleep(2000)
                                SnackBarConfig.clearSnackBarConfig()
                            }
                        }
                    }
                )
                navController.popBackStack()
            }
        }
    } else {
        screenHeading = "Edit Goal"
        buttonText = "Save"
        buttonAction = {
            if (isNamevalid && isStepsValid) {
                toBeSavedGoal.goalName = goalName.value
                toBeSavedGoal.steps = goalSteps.value.toInt()
                editGoalViewModel.insertGoal(toBeSavedGoal)
                navController.popBackStack()
            }
        }
    }
    val focusManager = LocalFocusManager.current
    var isDarkMode = isSystemInDarkTheme()
    var buttonColor = Color.Black
    var arrowColor = Color.White
    if (isDarkMode) {
        buttonColor = Color.White
        arrowColor = Color.Black
    }
    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                content = {
                    FilledCircularIconButton(
                        icon = Icons.Default.ArrowBack,
                        backgroundColor = buttonColor,
                        arrowColor = arrowColor
                    ) {
                        navController.navigate(route = FitnessTrackerScreens.GoalsScreen.name)
                    }
                },
                elevation = 0.dp
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text(
                text = screenHeading,
                modifier = Modifier.padding(top = 10.dp),
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight(300)
            )
            BackgroundCard(
                modifier = Modifier.padding(top = 20.dp, start = 1.dp, end = 1.dp),
                elevation = 1
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    OutlinedInputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        valueState = goalName,
                        label = "Goal Name",
                        enabled = true,
                        isSingleLine = true,
                        keyBoardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        onAction = KeyboardActions {
                            if (!isNamevalid) return@KeyboardActions
                            keyboardController?.hide()
                            focusManager.moveFocus(FocusDirection.Down)
                        },
                        onValueChange = {
                            isNameTouched = true
                        }
                    )
                    if (!isNamevalid && isNameTouched) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Enter Valid Name" )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    OutlinedInputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        valueState = goalSteps,
                        label = "Steps",
                        enabled = true,
                        isSingleLine = true,
                        keyBoardType = KeyboardType.Number,
                        ImeAction.Default,
                        onAction = KeyboardActions {
                            if (!isStepsValid) return@KeyboardActions
                            keyboardController?.hide()
                        },
                        onValueChange = {
                            isStepsTouched = true
                        }
                    )
                    if (!isStepsValid && isStepsTouched) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Enter Valid Steps" )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = buttonAction,
                        enabled = isbuttonEnabled
                    ) {
                        Text(text = buttonText)
                    }
                }
            }
        }
    }
}