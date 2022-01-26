package io.dev00.fitnesstracker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import io.dev00.fitnesstracker.components.BackgroundCard
import io.dev00.fitnesstracker.components.OutlinedIconInputField

@ExperimentalComposeUiApi
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)

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

@Preview(showBackground = true)
@Composable
fun TopCard() {
//    val configuration = LocalConfiguration.current
//    val screenHeight = configuration.screenHeightDp.dp
//    val screenWidth = configuration.screenWidthDp.dp
//    Log.d("TAG", "TopCard: $screenWidth $screenHeight")
    val steps = 3000
    BackgroundCard {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "No Active Goal",
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight(500)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(text = "Number of Steps : ")
                Text(text = buildAnnotatedString {
                    append(
                        AnnotatedString(
                            steps.toString(),
                            spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                        )
                    )
                    append(
                        AnnotatedString(
                            text = " steps",
                        )
                    )
                })
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun BottomCard() {
    val steps = remember {
        mutableStateOf("")
    }
    val isValid = steps.value.trim().isNotEmpty() && steps.value.isDigitsOnly()
    var parsedValue = 0
    if (isValid) {
        parsedValue = steps.value.toInt()
    }
    val date = "23/01/2022"
    val keyboardController = LocalSoftwareKeyboardController.current
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
                    Text(text = date, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Change")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedIconInputField(
                    modifier = Modifier.fillMaxWidth(),
                    valueState = steps,
                    label = "Steps",
                    icon = Icons.Default.DirectionsWalk,
                    enabled = true,
                    isSingleLine = true,
                    onAction = KeyboardActions {
                        if (!isValid) return@KeyboardActions
                        keyboardController?.hide()
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        // save to db or server
                    }) {
                    Text(text = "Add")
                }
            }
        }
    }
}


