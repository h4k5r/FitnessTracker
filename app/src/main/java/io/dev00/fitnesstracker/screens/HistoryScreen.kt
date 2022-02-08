package io.dev00.fitnesstracker.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.dev00.fitnesstracker.components.BackgroundCard
import io.dev00.fitnesstracker.components.SimpleIconButton
import io.dev00.fitnesstracker.models.Steps
import io.dev00.fitnesstracker.navigation.ModalConfiguration
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
    var selectMonthDropDown by remember {
        mutableStateOf(false)
    }
    var selectedYear by remember {
        mutableStateOf(historyViewModel.getMonthAndYear().split("/")[1])
    }
    var selectYearDropDown by remember {
        mutableStateOf(false)
    }
    var steps =
        historyViewModel.selectedMonthSteps.collectAsState().value.sortedBy { it.day.toInt() }
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "History",
            modifier = Modifier,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight(300)
        )
        Text(
            text = "Pick Month and Year",
            modifier = Modifier.padding(top = 20.dp),
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
                                modifier = Modifier.clickable {
                                    selectedMonth = i.toString()
                                    historyViewModel.setMonthYear("${selectedMonth}/${selectedYear}")
                                    selectMonthDropDown = false
                                },
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
                                modifier = Modifier.clickable {
                                    selectedYear = i.toString()
                                    historyViewModel.setMonthYear("${selectedMonth}/${selectedYear}")
                                    selectYearDropDown = false
                                },
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

        LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
            items(steps) {
                StepHistoryItem(it, onDeleteClick = {
                    ModalConfiguration.setModalConfig(
                        title = "Delete History",
                        content = "Do you want do delete data on ${it.day}/${it.month}/${it.year}?",
                        show = true,
                        onYesClickHandler = {
                            historyViewModel.deleteSteps(it)
                            ModalConfiguration.clearModalConfig()
                        },
                        onNoClickHandler = {
                            ModalConfiguration.clearModalConfig()
                        }
                    )
                })
            }
        }
    }
}

@Composable
fun StepHistoryItem(step: Steps = Steps(), onDeleteClick: () -> Unit) {
    BackgroundCard(
        elevation = 1,
        modifier = Modifier.padding(bottom = 5.dp, start = 1.dp, end = 1.dp, top = 1.dp)
    ) {
        Box() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Icon(modifier = Modifier.size(40.dp), imageVector = Icons.Default.History, contentDescription = "History")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.padding(
                            start = 5.dp,
                            end = 10.dp,
                            top = 5.dp,
                            bottom = 5.dp
                        )
                    ) {
                        Row() {
                            Text(text = "Steps: ")
                            Text(text = step.steps.toString(), fontWeight = FontWeight.Bold)
                        }
                        Row() {
                            Text(text = "Date: ")
                            Text(
                                text = "${step.day}/${step.month}/${step.year}",
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                    SimpleIconButton(
                        modifier = Modifier.padding(5.dp),
                        icon = Icons.Default.Delete,
                        contentDescription = "Delete Goal",
                    ) {
                        onDeleteClick()
                    }
                }
            }
        }
    }
}
