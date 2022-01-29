package io.dev00.fitnesstracker.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.dev00.fitnesstracker.components.configuredDatePickerDialog
import io.dev00.fitnesstracker.models.Steps
import io.dev00.fitnesstracker.navigation.FitnessTrackerScreens
import io.dev00.fitnesstracker.utils.fetchCurrentDate
import io.dev00.fitnesstracker.viewModel.HistoryViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    historyViewModel: HistoryViewModel
) {
    val context = LocalContext.current
    var date by remember {
        mutableStateOf(fetchCurrentDate())
    }
    var steps = historyViewModel.selectedDateSteps.collectAsState().value[0]
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
            text = "Pick Date",
            modifier = Modifier.padding(top = 20.dp),
            fontSize = MaterialTheme.typography.h5.fontSize,
            fontWeight = FontWeight(500)
        )
        Row(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = buildAnnotatedString {
                append(AnnotatedString("Date:  "))
                append(AnnotatedString(date, spanStyle = SpanStyle(fontWeight = FontWeight.W500)))
            })
            Button(
                onClick = {
                    configuredDatePickerDialog(context = context) {
                        date = it
                        historyViewModel.setDate(it)
                        navController.backQueue.removeLast()
                        navController.navigate(route = FitnessTrackerScreens.HistoryScreen.name)
                    }.show()

                }, modifier = Modifier
                    .padding(start = 10.dp)
                    .height(20.dp), contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "Change", fontSize = 12.sp)
            }
        }
        if (steps.steps != 0) {
            Text(text = buildAnnotatedString {
                append(
                    AnnotatedString(
                        text = "Number of Steps: ",
                        spanStyle = SpanStyle(fontSize = MaterialTheme.typography.h5.fontSize)
                    )
                )
                append(
                    AnnotatedString(
                        text = steps.steps.toString(),
                        spanStyle = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.h5.fontSize
                        )
                    )
                )
            }, modifier = Modifier.padding(top = 40.dp))
            Button(
                onClick = {
                    navController.backQueue.removeLast()
                    navController.navigate(route = FitnessTrackerScreens.HistoryScreen.name)
                    historyViewModel.deleteSteps(steps = steps)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.error
                )
            ) {
                Text(text = "Delete History")
            }
        }
    }

}