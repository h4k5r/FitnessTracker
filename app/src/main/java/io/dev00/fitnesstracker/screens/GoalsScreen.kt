package io.dev00.fitnesstracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Flag
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.dev00.fitnesstracker.components.BackgroundCard
import io.dev00.fitnesstracker.components.FilledCircularIconButton
import io.dev00.fitnesstracker.components.SimpleIconButton
import io.dev00.fitnesstracker.models.Goal
@Composable
fun GoalsScreen(modifier: Modifier = Modifier, navController: NavController) {
    val goalList = listOf(
        Goal("Goal Name 1", 1, true),
        Goal("Goal Name 2", 2),
        Goal("Goal Name 3", 3),
        Goal("Goal Name 4", 4),
        Goal("Goal Name 5", 5),
        Goal("Goal Name 6", 6),
        Goal("Goal Name 7", 7),
        Goal("Goal Name 9", 8),
        Goal("Goal Name 10", 9),
        Goal("Goal Name 11", 10),
        Goal("Goal Name 12", 11),
    )
    val activeGoal = goalList.filter { it.isActive }
    val allGoals = goalList.filter { !it.isActive }
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column {
//            TopAppBar(
//                backgroundColor = Color.Transparent,
//                content = {
//                    FilledCircularIconButton(
//                        icon = Icons.Default.ArrowBack,
//                    ) {
//                        //Todo: go back
//                    }
//                },
//                elevation = 0.dp
//            )
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
                        Button(modifier = Modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
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
                                GoalCard(activeGoal[0], isActive = activeGoal.isNotEmpty())
                            } else {
                                Text(text = "No Active Goal")
                            }
                            Text(
                                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                                text = "All Goal",
                                fontSize = MaterialTheme.typography.h5.fontSize,
                                fontWeight = FontWeight(300)
                            )
                            LazyColumn(modifier = Modifier.padding(bottom = 20.dp)) {
                                items(allGoals) { goal ->
                                    GoalCard(goal = goal, hasActive = activeGoal.isNotEmpty())
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
fun GoalCard(goal: Goal, isActive: Boolean = false, hasActive: Boolean = false) {
    BackgroundCard(modifier = Modifier.padding(bottom = 5.dp, start = 1.dp, end = 1.dp), elevation = 1) {
        Box(
            modifier = Modifier.background(shape = RoundedCornerShape(15.dp),
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

                        }
                        SimpleIconButton(
                            modifier = Modifier.padding(5.dp),
                            icon = Icons.Default.Edit,
                            contentDescription = "Edit Goal",
                        ) {

                        }
                        if (!hasActive) {
                            Button(onClick = { /*TODO*/ }, contentPadding = PaddingValues(horizontal = 5.dp)) {
                                Text(text = "Activate")
                            }
                        }
                    } else {
                        Button(onClick = { /*TODO*/ },contentPadding = PaddingValues(horizontal = 5.dp)) {
                            Text(text = "Deactivate")
                        }
                    }
                }
            }
        }
    }
}













































































































