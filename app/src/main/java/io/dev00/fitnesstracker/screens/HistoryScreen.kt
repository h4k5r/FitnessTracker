package io.dev00.fitnesstracker.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.dev00.fitnesstracker.components.BackgroundCard
import io.dev00.fitnesstracker.components.ModalConfiguration
import io.dev00.fitnesstracker.components.SimpleIconButton
import io.dev00.fitnesstracker.components.TopBar
import io.dev00.fitnesstracker.models.Steps
import io.dev00.fitnesstracker.utils.fetchCurrentDate
import io.dev00.fitnesstracker.viewModel.HistoryViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    historyViewModel: HistoryViewModel,
) {
    var selectedMonth by remember {
        mutableStateOf(historyViewModel.getMonthAndYear().split("/")[0])
    }
//    var selectedMonth = historyViewModel.month.value
    var selectMonthDropDown by remember {
        mutableStateOf(false)
    }
    var selectedYear by remember {
        mutableStateOf(historyViewModel.getMonthAndYear().split("/")[1])
    }
//    var selectedYear = historyViewModel.year.value
    var selectYearDropDown by remember {
        mutableStateOf(false)
    }
    var showAll by remember {
        mutableStateOf(false)
    }
    val steps: List<Steps> = if (showAll) {
        historyViewModel.allSteps.collectAsState().value.sortedBy { it.day.toInt() }
            .reversed()
    } else {
        historyViewModel.selectedMonthSteps.collectAsState().value.filter {
            val stepDate = "${it.day}/${it.month}/${it.year}"
            fetchCurrentDate() != stepDate
        }.sortedBy { it.day.toInt() }
            .reversed()
    }
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                var isDropDownOpen by remember {
                    mutableStateOf(false)
                }
                TopBar(navController = navController, menuItems = listOf {
                    if (showAll)
                        Text(
                            modifier = Modifier
                                .clickable {
                                    it()
                                    isDropDownOpen = false
                                    ModalConfiguration.setModalConfig(
                                        title = "Delete All History",
                                        content = "Do you want do delete all History data?",
                                        show = true,
                                        onYesClickHandler = {
                                            historyViewModel.deleteAllSteps()
                                            ModalConfiguration.clearModalConfig()
                                        },
                                        onNoClickHandler = {
                                            ModalConfiguration.clearModalConfig()
                                        }
                                    )
                                }
                                .padding(5.dp),
                            text = "Delete All History"
                        )
                    else
                        Text(
                            modifier = Modifier
                                .clickable {
                                    it()
                                    isDropDownOpen = false
                                    ModalConfiguration.setModalConfig(
                                        title = "Delete History Month",
                                        content = "Do you want do delete data on the selected month?",
                                        show = true,
                                        onYesClickHandler = {
                                            historyViewModel.deleteMonth(
                                                month = selectedMonth,
                                                year = selectedYear
                                            )
                                            ModalConfiguration.clearModalConfig()
                                        },
                                        onNoClickHandler = {
                                            ModalConfiguration.clearModalConfig()
                                        }
                                    )
                                }
                                .padding(5.dp),
                            text = "Delete Month"
                        )

                }) {
                    Row(
                        modifier = Modifier
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "History",
                            modifier = Modifier,
                            fontSize = MaterialTheme.typography.h4.fontSize,
                            fontWeight = FontWeight(300)
                        )
                        Spacer(modifier = Modifier)
                        Button(onClick = { showAll = !showAll }) {
                            val buttonText: String = if (showAll) {
                                "View By Month"
                            } else {
                                "View All"
                            }
                            Text(text = buttonText)
                        }

                    }

                }

            }

            Spacer(modifier = Modifier
                .height(20.dp)
                .width(1.dp))
            if (!showAll) {
                Text(
                    text = "Pick Month and Year",
                    modifier = Modifier,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight(500)
                )
                Row() {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Selected Month: ")
                        Spacer(modifier = Modifier.width(10.dp))
                        Box() {
                            Text(text = selectedMonth, fontWeight = FontWeight.Bold)
                            DropdownMenu(
                                modifier = Modifier
                                    .fillMaxHeight(0.3f)
                                    .padding(top = 5.dp),
                                expanded = selectMonthDropDown,
                                onDismissRequest = { selectMonthDropDown = false }) {
                                for (i in 1..12) {
                                    Text(
                                        modifier = Modifier
                                            .clickable {
                                                selectedMonth = i.toString()
                                                historyViewModel.setMonthYear("${selectedMonth}/${selectedYear}")
                                                selectMonthDropDown = false
                                            }
                                            .padding(5.dp),
                                        text = i.toString()
                                    )
                                }
                            }
                        }
                        SimpleIconButton(
                            modifier = Modifier,
                            icon = Icons.Default.ArrowDropDown,
                            contentDescription = "Month Drop Down"
                        ) {
                            selectMonthDropDown = true
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Selected Year: ")
                        Spacer(modifier = Modifier.width(10.dp))
                        Box() {
                            Text(text = selectedYear, fontWeight = FontWeight.Bold)
                            DropdownMenu(
                                modifier = Modifier
                                    .fillMaxHeight(0.3f)
                                    .padding(top = 5.dp),
                                expanded = selectYearDropDown,
                                onDismissRequest = { selectYearDropDown = false }) {
                                for (i in 2000..2100) {
                                    Text(
                                        modifier = Modifier
                                            .clickable {
                                                selectedYear = i.toString()
                                                historyViewModel.setMonthYear("${selectedMonth}/${selectedYear}")
                                                selectYearDropDown = false
                                            }
                                            .padding(5.dp),
                                        text = i.toString()
                                    )
                                }
                            }
                        }
                        SimpleIconButton(
                            modifier = Modifier,
                            icon = Icons.Default.ArrowDropDown,
                            contentDescription = "Year Drop Down"
                        ) {
                            selectYearDropDown = true
                        }
                    }
                }
            }

            LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                items(steps) {
                    StepHistoryItem(
                        it
                    )
                }
            }

        }
    }
}

@Composable
fun StepHistoryItem(
    step: Steps = Steps()
//                    , onDeleteClick: () -> Unit
) {
    val progress: Float
    val currentSteps = step.steps
    val target = step.target
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
    BackgroundCard(
        elevation = 1,
        modifier = Modifier.padding(bottom = 5.dp, start = 1.dp, end = 1.dp, top = 1.dp)
    ) {
        Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)) {
            Box() {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                ) {
//                    Icon(
//                        modifier = Modifier.size(40.dp),
//                        imageVector = Icons.Default.History,
//                        contentDescription = "History"
//                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.padding(
                                top = 5.dp,
                                bottom = 5.dp
                            )
                        ) {
//                            Row() {
//                                Text(text = "Steps: ")
//                                Text(text = step.steps.toString(), fontWeight = FontWeight.Bold)
//                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row() {
                                    Text(text = "Date: ")
                                    Text(
                                        text = "${step.day}/${step.month}/${step.year}",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Row() {
                                    Text(text = "Goal Name: ")
                                    Text(
                                        text = step.goalName,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                        }
//                    SimpleIconButton(
//                        modifier = Modifier.padding(5.dp),
//                        icon = Icons.Default.Delete,
//                        contentDescription = "Delete Goal",
//                    ) {
//                        onDeleteClick()
//                    }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
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
                val percentValues = if (target ==0) {
                    0
                } else {
                    (currentSteps.toFloat() / target * 100).toInt()
                }
                Text(
                    text = "$percentValues % Done",
                    fontSize = 13.sp
                )
                Text(text = buildAnnotatedString {
                    append(AnnotatedString("Target : "))
                    append(
                        AnnotatedString(
                            target.toString(),
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
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)) {
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
        }
    }
}
