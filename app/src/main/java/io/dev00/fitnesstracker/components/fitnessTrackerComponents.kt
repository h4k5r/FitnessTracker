package io.dev00.fitnesstracker.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.ThumbUp,
    contentDescription:String = "",
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
fun BottomNavBar(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(start = 50.dp, end = 50.dp, top = 20.dp, bottom = 20.dp),
        elevation = 1.dp,
        shape = CircleShape.copy(all = CornerSize(100)),
    ) {
        Surface(color = Color(0xFFC4C4C4)) {
            Row(
                modifier = modifier
                    .clip(shape = CircleShape)
                    .background(Color.Transparent)
                    .padding(top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
                Icon(imageVector = Icons.Filled.Flag, contentDescription = "Goals")
                Icon(imageVector = Icons.Filled.History, contentDescription = "History")
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
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    label: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyBoardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        modifier = modifier,
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.DirectionsWalk,
                contentDescription = "Walk Icon"
            )
        },
        singleLine = isSingleLine,
        textStyle = MaterialTheme.typography.body1,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}