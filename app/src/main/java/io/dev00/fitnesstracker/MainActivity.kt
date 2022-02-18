package io.dev00.fitnesstracker

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import io.dev00.fitnesstracker.navigation.FitnessTrackerNavigation
import io.dev00.fitnesstracker.screens.SplashScreen

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalComposeUiApi
@Composable
fun App() {
    var showSplash by remember {
        mutableStateOf(true)
    }
    val timer = object: CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {

        }
        override fun onFinish() {
            showSplash = false
        }
    }

//    SplashScreen()

    AnimatedVisibility(
        visible = showSplash,
        exit = shrinkVertically() + fadeOut() + shrinkHorizontally()
    ) {
        SplashScreen()
        timer.start()
    }
    AnimatedVisibility(
        visible = !showSplash,
        enter =  expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(
            initialAlpha = 0f
        )
    ) {
        FitnessTrackerNavigation()
    }
}





