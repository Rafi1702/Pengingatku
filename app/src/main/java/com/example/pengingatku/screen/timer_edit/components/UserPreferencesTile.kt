package com.example.pengingatku.screen.timer_edit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pengingatku.utils.bottomBorder

@Composable

fun UserPreferenceTile(headlineText: String) {
    ListItem(
        modifier = Modifier.bottomBorder(
            color = MaterialTheme.colorScheme.onSurface,
            height = 1f
        ),
        headlineContent = {
            Text(headlineText)
        },
        supportingContent = {
            Text("Homecoming", color = MaterialTheme.colorScheme.tertiary)
        },

        trailingContent = {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 16.dp)
                )
                Switch(
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    checked = true,
                    onCheckedChange = {

                    }
                )
            }

        },

        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),

        )
}