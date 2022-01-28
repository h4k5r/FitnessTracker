package io.dev00.fitnesstracker.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.DirectionsWalk
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.dev00.fitnesstracker.navigation.FitnessTrackerScreens

@Preview
@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.ThumbUp,
    contentDescription: String = "",
    onClick: () -> Unit = {}
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            modifier = modifier.padding(5.dp),
            imageVector = icon,
            contentDescription = contentDescription,
        )
    }
}


@Composable
fun FilledCircularIconButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    arrowColor: Color = Color.White,
    backgroundColor: Color = Color.Black,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(shape = CircleShape, color = backgroundColor)
            .clickable { onClick() }) {
        Icon(
            modifier = Modifier.padding(5.dp),
            imageVector = icon,
            contentDescription = "Back",
            tint = arrowColor
        )
    }
}

@Composable
fun BottomNavBar(modifier: Modifier = Modifier, navController: NavController,activeScreen:MutableState<String>) {
    val darkTheme: Boolean = isSystemInDarkTheme()
    var background = Color.Transparent
    var homeIconTint: Color
    var goalIconTint: Color
    var historyIconTint: Color

    if (!darkTheme) {
        background = Color(0xFFE3E3E3)
    }
    if (!darkTheme) {
        homeIconTint = Color.Black
        if (activeScreen.value == FitnessTrackerScreens.HomeScreen.name) {
            homeIconTint = Color.Gray
        }
        goalIconTint = Color.Black
        if (activeScreen.value == FitnessTrackerScreens.GoalsScreen.name) {
            goalIconTint = Color.Gray
        }
        historyIconTint = Color.Black
        if (activeScreen.value == FitnessTrackerScreens.HistoryScreen.name) {
            historyIconTint = Color.Gray
        }
    } else {
        homeIconTint = Color.Gray
        if (activeScreen.value == FitnessTrackerScreens.HomeScreen.name) {
            homeIconTint = Color.Black
        }
        goalIconTint = Color.Gray
        if (activeScreen.value == FitnessTrackerScreens.GoalsScreen.name) {
            goalIconTint = Color.Black
        }
        historyIconTint = Color.Gray
        if (activeScreen.value == FitnessTrackerScreens.HistoryScreen.name) {
            historyIconTint = Color.Black
        }
    }

    Card(
        modifier = Modifier
            .padding(start = 50.dp, end = 50.dp, top = 20.dp, bottom = 20.dp),
        elevation = 1.dp,
        shape = CircleShape.copy(all = CornerSize(100)),
    ) {

        Surface(color = background) {
            Row(
                modifier = modifier
                    .clip(shape = CircleShape)
                    .background(Color.Transparent)
                    .padding(top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    modifier = Modifier.clickable {
                        if (activeScreen.value != FitnessTrackerScreens.HomeScreen.name) {
                            navController.navigate(route = FitnessTrackerScreens.HomeScreen.name)
                            activeScreen.value = FitnessTrackerScreens.HomeScreen.name
                        }
                    },
                    tint = homeIconTint
                )
                Icon(
                    imageVector = Icons.Filled.Flag,
                    contentDescription = "Goals",
                    modifier = Modifier.clickable {
                        if (activeScreen.value != FitnessTrackerScreens.GoalsScreen.name) {
                            navController.navigate(route = FitnessTrackerScreens.GoalsScreen.name)
                            activeScreen.value = FitnessTrackerScreens.GoalsScreen.name
                        }
                    },
                    tint = goalIconTint
                )
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = "History",
                    modifier = Modifier.clickable {
                        if (activeScreen.value != FitnessTrackerScreens.HistoryScreen.name) {
                            navController.navigate(route = FitnessTrackerScreens.HistoryScreen.name)
                            activeScreen.value = FitnessTrackerScreens.HistoryScreen.name
                        }
                    },
                    tint = historyIconTint
                )
            }
        }
    }
}

@Composable
fun BackgroundCard(
    modifier: Modifier = Modifier,
    elevation: Int = 5,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = elevation.dp
    ) {
        content()
    }
}

@Composable
fun OutlinedIconInputField(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    label: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyBoardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        modifier = modifier,
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Walk Icon"
            )
        },
        singleLine = isSingleLine,
        textStyle = MaterialTheme.typography.body1,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}
@Composable
fun OutlinedInputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    label: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyBoardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        modifier = modifier,
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = { Text(text = label) },
        singleLine = isSingleLine,
        textStyle = MaterialTheme.typography.body1,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}

@Composable
fun CheckBoxWithText(modifier: Modifier = Modifier,checkedState:MutableState<Boolean>,text:String) {
    Row(modifier = modifier) {
        Text(modifier = Modifier.padding(end = 10.dp), text = text)
        Checkbox(checked = checkedState.value, onCheckedChange = {
            checkedState.value = it
        } )
    }
}