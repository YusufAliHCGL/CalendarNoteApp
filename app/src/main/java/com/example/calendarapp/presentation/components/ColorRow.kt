package com.example.calendarapp.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendarapp.presentation.util.Util

@Composable
fun ColorRow(
    modifier: Modifier = Modifier,
    text: String,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 2.dp),
        text = text,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = if (isSystemInDarkTheme()) Color(0xFFFFFFFF) else Color(0xFF000000)
    )

    Spacer(modifier = Modifier.height(5.dp))
    LazyRow(
        state = rememberLazyListState(
            initialFirstVisibleItemIndex = Util.colorList.indexOfFirst {
                it == selectedColor
            }
        )
    ) {
        items(Util.colorList) { colorCode ->
            ColorBox(
                size = 60.dp,
                borderStroke = 3.dp,
                color = colorCode,
                isSelected = colorCode == selectedColor
            ) { color ->
                onColorSelected(color)
            }
        }
    }

}