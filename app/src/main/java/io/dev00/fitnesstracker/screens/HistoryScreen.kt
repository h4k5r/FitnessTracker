package io.dev00.fitnesstracker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HistoryScreen(modifier: Modifier = Modifier, navController: NavController) {
    val date = "26/01/2022"
    val numberOfSteps = "10000"
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
                onClick = { /*TODO*/ }, modifier = Modifier
                    .padding(start = 10.dp)
                    .height(20.dp), contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "Change", fontSize = 12.sp)
            }
        }
        Button(
            onClick = { /*TODO*/ }, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Text(text = "Search")
        }
        Text(text = buildAnnotatedString {
            append(
                AnnotatedString(
                    text = "Number of Steps: ",
                    spanStyle = SpanStyle(fontSize = MaterialTheme.typography.h5.fontSize)
                )
            )
            append(
                AnnotatedString(
                    text = numberOfSteps,
                    spanStyle = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.h5.fontSize
                    )
                )
            )
        }, modifier = Modifier.padding(top = 40.dp))
        if (numberOfSteps.isNotEmpty()) {
            Button(
                onClick = {
                          /*TODO*/
                          }, modifier = Modifier
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