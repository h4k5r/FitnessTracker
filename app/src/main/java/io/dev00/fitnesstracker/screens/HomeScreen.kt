package io.dev00.fitnesstracker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.dev00.fitnesstracker.components.BackgroundCard
import io.dev00.fitnesstracker.components.InputField

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 30.dp, end = 20.dp)
    ) {
        Text(
            text = "Hello",
            modifier = Modifier,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight(300)
        )
        Spacer(modifier = Modifier.height(30.dp))
        TopCard()
        Spacer(modifier = Modifier.height(30.dp))
        BottomCard()

    }
}

@Composable
fun TopCard() {
    val steps = 3000
    BackgroundCard{
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "No Active Goal",
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight(500)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(text = "Number of Steps : ")
                Text(text = "$steps Steps")
            }
        }
    }
}

@Composable
fun BottomCard() {
    val steps =  remember {
        mutableStateOf("")
    }
    val date = "23/01/2022"
    Column {
        BackgroundCard {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Add Steps",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight(500)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Date : ")
                    Text(text = date)
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Change")
                    }
                }
                InputField(modifier = Modifier.fillMaxWidth(), valueState = steps, label = "Steps", enabled = true, isSingleLine = true)
            }
        }
    }
}


