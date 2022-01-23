package io.dev00.fitnesstracker.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsWalk
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun BackgroundCard(modifier: Modifier = Modifier,content : @Composable () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
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
    imeAction: ImeAction = ImeAction.Next,
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