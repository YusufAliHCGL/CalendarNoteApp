package com.example.calendarapp.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun SortButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onButtonClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 22.sp
        )
        RadioButton(
            selected = isSelected,
            onClick = onButtonClicked,
            colors = RadioButtonDefaults.colors(
                selectedColor = if(isSystemInDarkTheme()) Color.White else Color.Black,
                unselectedColor = if(isSystemInDarkTheme()) Color.White else Color.Black
            )
        )
    }
}