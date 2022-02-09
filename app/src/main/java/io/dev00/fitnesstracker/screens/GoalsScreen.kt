package io.dev00.fitnesstracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Flag
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.dev00.fitnesstracker.components.BackgroundCard
import io.dev00.fitnesstracker.components.SimpleIconButton
import io.dev00.fitnesstracker.models.Goal
import io.dev00.fitnesstracker.models.Preference
import io.dev00.fitnesstracker.navigation.FitnessTrackerScreens
import io.dev00.fitnesstracker.navigation.ModalConfiguration
import io.dev00.fitnesstracker.viewModel.GoalsViewModel

@Composable
fun GoalsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    goalsViewModel: GoalsViewModel,
) {
    val preferencesList = goalsViewModel.preferences.value
    val activeGoal = remember {
        goalsViewModel.activeGoal.value
    }

    val inactiveGoals = remember {
        goalsViewModel.inactiveGoals.value
    }

    var editableGoal by remember {
        mutableStateOf(Preference(name = "editable Goal", value = true))
    }

    val editable = preferencesList.find { it.name == "editable Goal" }
    if (editable != null) {
        editableGoal = editable
    }
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column {
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Goals",
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight(300)
            )
            Box(
                modifier = Modifier
            ) {
                Scaffold(
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp),
                    bottomBar = {
                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
                            /*TODO*/
                            navController.navigate(route = FitnessTrackerScreens.AddGoalScreen.name)
                        }) {
                            Text(text = "Create New Goal")
                        }
                    }) { padding ->
                    BackgroundCard(modifier = Modifier.padding(padding), elevation = 0) {
                        Column() {
                            Text(
                                modifier = Modifier.padding(bottom = 10.dp),
                                text = "Active Goal",
                                fontSize = MaterialTheme.typography.h5.fontSize,
                                fontWeight = FontWeight(300)
                            )
                            if (activeGoal.isNotEmpty()) {
                                GoalCard(
                                    activeGoal[0],
                                    isActive = activeGoal.isNotEmpty(),
                                    onDeactivateClick = {
                                        navController.backQueue.removeLast()
                                        navController.navigate(route = FitnessTrackerScreens.GoalsScreen.name)
                                        goalsViewModel.deactivateGoal(activeGoal[0])
                                    }, isEditable = false
                                )
                            } else {
                                Text(text = "No Active Goal")
                            }
                            Text(
                                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                                text = "All Goals",
                                fontSize = MaterialTheme.typography.h5.fontSize,
                                fontWeight = FontWeight(300)
                            )
                            LazyColumn(modifier = Modifier.padding(bottom = 20.dp)) {
                                items(inactiveGoals) { goal ->
                                    var onActivateClick = {
                                        if (activeGoal.isNotEmpty()) {
                                            goalsViewModel.deactivateGoal(activeGoal[0])
                                        }
                                        goalsViewModel.activateGoal(goal = goal)
                                        navController.backQueue.removeLast()
                                        navController.navigate(route = FitnessTrackerScreens.GoalsScreen.name)
                                    }
                                    GoalCard(
                                        goal = goal,
                                        hasActive = activeGoal.isNotEmpty(),
                                        onDeleteClick = {
                                            ModalConfiguration.setModalConfig(
                                                title = "Delete Goal",
                                                content = "Do you want do delete goal: ${goal.goalName}?",
                                                onYesClickHandler = {
                                                    goalsViewModel.deleteGoal(goal = goal)
                                                    ModalConfiguration.clearModalConfig()
                                                    navController.backQueue.removeLast()
                                                    navController.navigate(route = FitnessTrackerScreens.GoalsScreen.name)
                                                },
                                                onNoClickHandler = {
                                                    ModalConfiguration.clearModalConfig()

                                                },
                                                show = true
                                            )
                                        },
                                        onEditClick = {
                                            navController.navigate(route = FitnessTrackerScreens.EditGoalScreen.name + "/${goal.id}")
                                        },
                                        onActivateClick = onActivateClick,
                                        isEditable = editableGoal.value
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GoalCard(
    goal: Goal,
    isActive: Boolean = false,
    hasActive: Boolean = false,
    isEditable: Boolean,
    onDeleteClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onActivateClick: () -> Unit = {},
    onDeactivateClick: () -> Unit = {},
) {
    BackgroundCard(
        modifier = Modifier.padding(bottom = 5.dp, start = 1.dp, end = 1.dp),
        elevation = 1
    ) {
        Box(
            modifier = Modifier.background(
                shape = RoundedCornerShape(15.dp),
                color = Color.Transparent
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row() {
                    Icon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .size(40.dp),
                        imageVector = Icons.Default.Flag,
                        contentDescription = "Goal",
                    )
                    Column {
                        Text(text = goal.goalName)
                        Text(text = buildAnnotatedString {
                            append(
                                AnnotatedString(
                                    text = goal.steps.toString(),
                                    spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                                )
                            )
                            append(
                                AnnotatedString(" steps")
                            )
                        })
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isActive) {
                        SimpleIconButton(
                            modifier = Modifier.padding(5.dp),
                            icon = Icons.Default.Delete,
                            contentDescription = "Delete Goal",
                        ) {
                            onDeleteClick()
                        }
                        if (isEditable) {
                            SimpleIconButton(
                                modifier = Modifier.padding(5.dp),
                                icon = Icons.Default.Edit,
                                contentDescription = "Edit Goal",
                            ) {
                                onEditClick()
                            }
                        }
//                        if (!hasActive) {
                        Button(
                            onClick = {
                                onActivateClick()
                            },
                            contentPadding = PaddingValues(horizontal = 5.dp)
                        ) {
                            Text(text = "Activate")
                        }
//                        }
                    } else {
                        Button(
                            onClick = { onDeactivateClick() },
                            contentPadding = PaddingValues(horizontal = 5.dp)
                        ) {
                            Text(text = "Deactivate")
                        }
                    }
                }
            }
        }
    }
}













































































































