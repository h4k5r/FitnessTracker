package io.dev00.fitnesstracker.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    var editableState by remember {
        mutableStateOf(true)
    }
    var init by remember {
        mutableStateOf(true)
    }
    var editableGoal by remember {
        mutableStateOf(Preference(name = "editable Goal", value = true))
    }
    if (init) {
        init = false
        val editable = preferencesList.find { it.name == "editable Goal" }
        if (editable != null) {
            editableGoal = editable
            editableState = editable.value
        }
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
                            backgroundColor = Color.Black
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Make Goals Editable")
            Switch(checked = editableState, onCheckedChange = {
                editableState = it
                editableGoal.value = it
                settingsViewModel.setPreference(editableGoal)
            })
        }
    }
}