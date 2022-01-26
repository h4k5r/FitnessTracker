package io.dev00.fitnesstracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import io.dev00.fitnesstracker.components.BackgroundCard
import io.dev00.fitnesstracker.components.CheckBoxWithText
import io.dev00.fitnesstracker.components.FilledCircularIconButton
import io.dev00.fitnesstracker.components.OutlinedInputField

@ExperimentalComposeUiApi
@Preview
@Composable
fun AddOrEditGoalScreen(modifier: Modifier = Modifier, isAdd: Boolean = true, goalId: String = "") {
    var isInitial by remember {
        mutableStateOf(true)
    }

    var goalName = remember {
        mutableStateOf("")
    }
    val isNamevalid = goalName.value.trim().isNotEmpty()

    var goalSteps = remember {
        mutableStateOf("")
    }
    val isStepsValid = goalSteps.value.trim().isNotEmpty() && goalSteps.value.isDigitsOnly()

    var isEditable = remember {
        mutableStateOf(false)
    }
    var isbuttonEnabled = isNamevalid && isStepsValid
    if (isInitial && !isAdd) {
        //fetch data from database
        isInitial = false
        isbuttonEnabled = false
        goalName.value = "Test Goal"
        goalSteps.value = "100"
    }

    var screenHeading: String

    var buttonText: String

    var buttonAction = {}
    if (isAdd) {
        screenHeading = "Create Goal"
        buttonText = "Add"
        buttonAction = {
            //Todo Create Goal Action
        }
    } else {
        screenHeading = "Edit Goal"
        buttonText = "Save"
        buttonAction = {
            //Todo Save Goal Action
        }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                content = {
                    FilledCircularIconButton(
                        icon = Icons.Default.ArrowBack,
                        backgroundColor = Color.Black
                    ) {
                        //Todo: go back
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
                        }
                    )
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
                        }
                    )
                    if (isAdd) {
                        CheckBoxWithText(
                            modifier = Modifier.padding(bottom = 20.dp),
                            checkedState = isEditable,
                            text = "Editable"
                        )
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