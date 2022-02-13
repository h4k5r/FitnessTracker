package io.dev00.fitnesstracker.components

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.DirectionsWalk
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import io.dev00.fitnesstracker.navigation.FitnessTrackerScreens
import java.util.*

@Preview
@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.ThumbUp,
    contentDescription: String = "",
    onClick: () -> Unit = {}
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            modifier = modifier.padding(5.dp),
            imageVector = icon,
            contentDescription = contentDescription,
        )
    }
}


@Composable
fun FilledCircularIconButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    arrowColor: Color = Color.White,
    backgroundColor: Color = Color.Black,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(shape = CircleShape, color = backgroundColor)
            .clickable { onClick() }) {
        Icon(
            modifier = Modifier.padding(5.dp),
            imageVector = icon,
            contentDescription = "Back",
            tint = arrowColor
        )
    }
}

@Composable
fun TopBar(navController: NavController, content: @Composable () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var isDropDownOpen by remember {
            mutableStateOf(false)
        }
        content()
        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
            SimpleIconButton(
                modifier = Modifier,
                icon = Icons.Default.MoreVert,
                contentDescription = "Settings Drop Down"
            ) {
                isDropDownOpen = true
            }
            Box() {
                DropdownMenu(
                    modifier = Modifier,
                    expanded = isDropDownOpen,
                    onDismissRequest = { isDropDownOpen = false }) {
                    Text(
                        modifier = Modifier
                            .clickable {
                                isDropDownOpen = false
                                navController.navigate(route = FitnessTrackerScreens.SettingsScreen.name)
                            }
                            .padding(5.dp),
                        text = "Settings"
                    )

                }
            }

        }
    }
}

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    activeScreen: MutableState<String>
) {
    val darkTheme: Boolean = isSystemInDarkTheme()
    var background = Color.Transparent
    var homeIconTint: Color
    var goalIconTint: Color
    var historyIconTint: Color

    if (!darkTheme) {
        background = Color(0xFFE3E3E3)
    }
    if (!darkTheme) {
        homeIconTint = Color.Black
        if (activeScreen.value == FitnessTrackerScreens.HomeScreen.name) {
            homeIconTint = Color.Gray
        }
        goalIconTint = Color.Black
        if (activeScreen.value == FitnessTrackerScreens.GoalsScreen.name) {
            goalIconTint = Color.Gray
        }
        historyIconTint = Color.Black
        if (activeScreen.value == FitnessTrackerScreens.HistoryScreen.name) {
            historyIconTint = Color.Gray
        }
    } else {
        homeIconTint = Color.Gray
        if (activeScreen.value == FitnessTrackerScreens.HomeScreen.name) {
            homeIconTint = Color.Black
        }
        goalIconTint = Color.Gray
        if (activeScreen.value == FitnessTrackerScreens.GoalsScreen.name) {
            goalIconTint = Color.Black
        }
        historyIconTint = Color.Gray
        if (activeScreen.value == FitnessTrackerScreens.HistoryScreen.name) {
            historyIconTint = Color.Black
        }
    }

    Card(
        modifier = Modifier
            .padding(start = 50.dp, end = 50.dp, top = 20.dp, bottom = 20.dp),
        elevation = 1.dp,
        shape = CircleShape.copy(all = CornerSize(100)),
    ) {

        Surface(color = background) {
            Row(
                modifier = modifier
                    .clip(shape = CircleShape)
                    .background(Color.Transparent)
                    .padding(top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    modifier = Modifier.clickable {
                        if (activeScreen.value != FitnessTrackerScreens.HomeScreen.name) {
                            navController.navigate(route = FitnessTrackerScreens.HomeScreen.name)
                            activeScreen.value = FitnessTrackerScreens.HomeScreen.name
                        }
                    },
                    tint = homeIconTint
                )
                Icon(
                    imageVector = Icons.Filled.Flag,
                    contentDescription = "Goals",
                    modifier = Modifier.clickable {
                        if (activeScreen.value != FitnessTrackerScreens.GoalsScreen.name) {
                            navController.navigate(route = FitnessTrackerScreens.GoalsScreen.name)
                            activeScreen.value = FitnessTrackerScreens.GoalsScreen.name
                        }
                    },
                    tint = goalIconTint
                )
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = "History",
                    modifier = Modifier.clickable {
                        if (activeScreen.value != FitnessTrackerScreens.HistoryScreen.name) {
                            navController.navigate(route = FitnessTrackerScreens.HistoryScreen.name)
                            activeScreen.value = FitnessTrackerScreens.HistoryScreen.name
                        }
                    },
                    tint = historyIconTint
                )
            }
        }
    }
}

@Composable
fun BackgroundCard(
    modifier: Modifier = Modifier,
    elevation: Int = 5,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = elevation.dp
    ) {
        content()
    }
}

@Composable
fun OutlinedIconInputField(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    label: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    error:Boolean = false,
    keyBoardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
    onValueChange: (content:String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        value = valueState.value,
        onValueChange = {
            valueState.value = it
            onValueChange(it);
        },
        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Walk Icon"
            )
        },
        singleLine = isSingleLine,
        textStyle = MaterialTheme.typography.body1,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = imeAction),
        keyboardActions = onAction,
        isError = error
    )
}

@Composable
fun OutlinedInputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    label: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyBoardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
    onValueChange: (content:String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        value = valueState.value,
        onValueChange = {
            valueState.value = it
            onValueChange(it);
        },
        label = { Text(text = label) },
        singleLine = isSingleLine,
        textStyle = MaterialTheme.typography.body1,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}

@Composable
fun CheckBoxWithText(
    modifier: Modifier = Modifier,
    checkedState: MutableState<Boolean>,
    text: String
) {
    Row(modifier = modifier) {
        Text(modifier = Modifier.padding(end = 10.dp), text = text)
        Checkbox(checked = checkedState.value, onCheckedChange = {
            checkedState.value = it
        })
    }
}


fun configuredDatePickerDialog(context: Context, onDatePicked: (String) -> Unit): DatePickerDialog {
    val year: Int
    val month: Int
    val day: Int
    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            onDatePicked("${day}/${month + 1}/${year}")
        }, year, month, day
    )
    return datePickerDialog
}

@Composable
fun YesOrNoModal(
    title: String,
    content: String,
    onYesClickHandler: () -> Unit,
    onNoClickHandler: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color(0x80000000))
            .fillMaxWidth()
            .fillMaxHeight()
            .zIndex(1000f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackgroundCard(modifier = Modifier.fillMaxWidth(0.9f)) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.h5.fontSize
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = content, textAlign = TextAlign.Center)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { onYesClickHandler() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                    ) {
                        Text(text = "Yes")
                    }
                    Button(
                        onClick = { onNoClickHandler() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondaryVariant)
                    ) {
                        Text(text = "No")
                    }
                }
            }
        }
    }
}

object ModalConfiguration {
    var title = mutableStateOf("")
    var content = mutableStateOf("")
    var onYesClickHandler = mutableStateOf({})
    var onNoClickHandler = mutableStateOf({})
    var show = mutableStateOf(false)

    fun setModalConfig(
        title: String,
        content: String,
        onYesClickHandler: () -> Unit,
        onNoClickHandler: () -> Unit,
        show: Boolean
    ) {
        this.show.value = show
        this.title.value = title
        this.content.value = content
        this.onNoClickHandler.value = onNoClickHandler
        this.onYesClickHandler.value = onYesClickHandler
    }

    fun clearModalConfig() {
        this.show.value = false
        this.title.value = ""
        this.content.value = ""
        this.onNoClickHandler.value = {}
        this.onYesClickHandler.value = {}
    }
}


@Composable
fun ConfiguredSnackBar(content: String, buttonText: String, buttonAction: () -> Unit) {
    Snackbar(
        modifier = Modifier.zIndex(1f),
        action = {
            if (SnackBarConfig.showButton.value) {
                Button(onClick = { buttonAction() }) {
                    Text(
                        text = AnnotatedString(
                            buttonText,
                            SpanStyle(fontWeight = FontWeight.Bold)
                        )
                    )
                }
            }
        }
    ) {
        Text(text = content)
    }
}

object SnackBarConfig {
    var content = mutableStateOf("")
    var buttonText = mutableStateOf("")
    var buttonAction = mutableStateOf({})
    var show = mutableStateOf(false)
    var showButton = mutableStateOf(true)

    fun setSnackBarConfig(
        content: String,
        buttonText: String = "",
        buttonAction: () -> Unit = {},
        show: Boolean,
        showButton: Boolean
    ) {
        this.content.value = content
        this.buttonText.value = buttonText
        this.buttonAction.value = buttonAction
        this.show.value = show
        this.showButton.value = showButton
    }

    fun clearSnackBarConfig() {
        this.content.value = ""
        this.buttonText.value = ""
        this.buttonAction.value = {}
        this.show.value = false
        this.showButton.value = true
    }
}