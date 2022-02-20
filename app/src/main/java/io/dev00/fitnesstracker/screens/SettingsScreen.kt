package io.dev00.fitnesstracker.screens

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.dev00.fitnesstracker.components.FilledCircularIconButton
import io.dev00.fitnesstracker.models.Preference
import io.dev00.fitnesstracker.viewModel.SettingsViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    settingsViewModel: SettingsViewModel
) {
    val preferencesList = settingsViewModel.preferences.value
    var init by remember {
        mutableStateOf(true)
    }
    var editableState = remember {
        mutableStateOf(true)
    }
    var editableGoal by remember {
        mutableStateOf(Preference(name = "editable Goal", value = true))
    }
    var historicalState = remember {
        mutableStateOf(true)
    }
    var historicalRecording by remember {
        mutableStateOf(Preference(name = "historical editing", value = true))
    }
    if (init) {
        init = false
        val editable = preferencesList.find { it.name == "editable Goal" }
        val historical= preferencesList.find { it.name == "historical editing" }

        if (editable != null) {
            editableGoal = editable
            editableState.value = editable.value
        }
        if (historical != null) {
            historicalRecording = historical
            historicalState.value = historical.value
        }
    }
    var isDarkMode = isSystemInDarkTheme()
    var buttonColor = Color.Black
    var arrowColor = Color.White
    if (isDarkMode) {
        buttonColor = Color.White
        arrowColor = Color.Black
    }
    Log.d("TAG", "SettingsScreen: ${editableState}")
    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                content = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        FilledCircularIconButton(
                            icon = Icons.Default.ArrowBack,
                            backgroundColor = buttonColor,
                            arrowColor = arrowColor
                        ) {
                            navController.popBackStack()
                        }
                        Text(
                            text = "Settings",
                            modifier = Modifier.padding(start = 20.dp),
                            fontSize = MaterialTheme.typography.h4.fontSize,
                            fontWeight = FontWeight(300)
                        )
                    }
                },
                elevation = 0.dp
            )
        }
    ) {
        Column() {
            SettingsItem(
                settingsText = "Make Goals Editable",
                editableState = editableState,
                onCheckedChange = {
                    editableState.value = it
                    editableGoal.value = it
                    settingsViewModel.setPreference(editableGoal)
                })
            SettingsItem(
                settingsText = "Make Historical Recording",
                editableState = historicalState,
                onCheckedChange = {
                    historicalState.value = it
                    historicalRecording.value = it
                    settingsViewModel.setPreference(historicalRecording)
                })
        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(20.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(text = "Make Goals Editable")
//            Switch(checked = editableState.value, onCheckedChange = {
//                editableState.value = it
//                editableGoal.value = it
//                settingsViewModel.setPreference(editableGoal)
//            })
//        }
    }
}

@Composable
fun SettingsItem(
    settingsText: String,
    editableState: MutableState<Boolean>,
    onCheckedChange: (changed: Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = settingsText)
        Switch(checked = editableState.value, onCheckedChange = {
            onCheckedChange(it)
        })
    }
}