package com.example.pengingatku.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable

@Composable
fun SwitchToggle(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit){
    Switch(
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.onSecondaryContainer,
            uncheckedThumbColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
    )
}
