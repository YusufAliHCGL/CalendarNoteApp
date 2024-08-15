package com.example.calendarapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.calendarapp.presentation.util.Util

@Composable
fun ColorBox(
    modifier: Modifier = Modifier,
    size: Dp,
    borderStroke: Dp,
    color: Color,
    isSelected: Boolean = false,
    onColorSelected: (Color) -> Unit
) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .size(size)
            .clip(CircleShape)
            .border(
                border = if (isSelected) BorderStroke(width = borderStroke, brush = Util.horizontalBoxBorderBrush)
                else BorderStroke(width = borderStroke, color = color),
                shape = CircleShape
            )
            .background(color)
            .clickable {
                onColorSelected(color)
            }
    )
}