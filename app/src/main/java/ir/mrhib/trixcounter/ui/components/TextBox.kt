package ir.mrhib.trixcounter.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.mrhib.trixcounter.ui.theme.vazir


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextBox(
    label: String,
    keyboardType: KeyboardType,
    state: MutableState<String>,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = {
            state.value = it
        },
        enabled = enabled,
        label = {
            Text(
                text = label,
                fontFamily = vazir
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType, imeAction = ImeAction.Next
        ),
        trailingIcon = {
            if (state.value.isNotEmpty() && enabled) {
                IconButton(onClick = { state.value = "" }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        },
        modifier = modifier.padding(1.dp),
        textStyle = TextStyle(
            textAlign = TextAlign.Center,
            fontFamily = vazir,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    )
}