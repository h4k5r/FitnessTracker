package io.dev00.fitnesstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.dev00.fitnesstracker.components.BottomNavBar
import io.dev00.fitnesstracker.screens.GoalsScreen
import io.dev00.fitnesstracker.screens.HomeScreen
import io.dev00.fitnesstracker.ui.theme.FitnessTrackerTheme

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun App() {
    FitnessTrackerTheme {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomNavBar(modifier = Modifier.fillMaxWidth())
                }
            ) {
//                HomeScreen()
                GoalsScreen(Modifier.padding(it))
            }
        }
    }
}



