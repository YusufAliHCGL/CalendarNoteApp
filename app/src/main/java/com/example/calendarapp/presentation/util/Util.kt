package com.example.calendarapp.presentation.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import com.example.calendarapp.presentation.ui.theme.BabyBlue
import com.example.calendarapp.presentation.ui.theme.Beige
import com.example.calendarapp.presentation.ui.theme.Black
import com.example.calendarapp.presentation.ui.theme.Coral
import com.example.calendarapp.presentation.ui.theme.Crimson
import com.example.calendarapp.presentation.ui.theme.DarkGray
import com.example.calendarapp.presentation.ui.theme.DarkKhaki
import com.example.calendarapp.presentation.ui.theme.DeepPurple
import com.example.calendarapp.presentation.ui.theme.ForestGreen
import com.example.calendarapp.presentation.ui.theme.GoldenYellow
import com.example.calendarapp.presentation.ui.theme.Gray
import com.example.calendarapp.presentation.ui.theme.Green
import com.example.calendarapp.presentation.ui.theme.Khaki
import com.example.calendarapp.presentation.ui.theme.Lavender
import com.example.calendarapp.presentation.ui.theme.LightGoldenrodYellow
import com.example.calendarapp.presentation.ui.theme.LightGray
import com.example.calendarapp.presentation.ui.theme.LightGreen
import com.example.calendarapp.presentation.ui.theme.LightPink
import com.example.calendarapp.presentation.ui.theme.LightSalmon
import com.example.calendarapp.presentation.ui.theme.Lime
import com.example.calendarapp.presentation.ui.theme.Maroon
import com.example.calendarapp.presentation.ui.theme.MidnightBlue
import com.example.calendarapp.presentation.ui.theme.MintGreen
import com.example.calendarapp.presentation.ui.theme.OceanBlue
import com.example.calendarapp.presentation.ui.theme.Peach
import com.example.calendarapp.presentation.ui.theme.Purple
import com.example.calendarapp.presentation.ui.theme.Red
import com.example.calendarapp.presentation.ui.theme.RedOrange
import com.example.calendarapp.presentation.ui.theme.Salmon
import com.example.calendarapp.presentation.ui.theme.SkyBlue
import com.example.calendarapp.presentation.ui.theme.SunsetOrange
import com.example.calendarapp.presentation.ui.theme.Teal
import com.example.calendarapp.presentation.ui.theme.Violet
import com.example.calendarapp.ui.theme.*

object Util {
    val colorList = listOf(
        LightPink,
        Peach,
        Coral,
        Salmon,
        LightSalmon,
        RedOrange,
        SunsetOrange,
        Red,
        Crimson,
        Maroon,
        Lavender,
        Violet,
        DeepPurple,
        Purple,
        SkyBlue,
        BabyBlue,
        OceanBlue,
        MidnightBlue,
        Teal,
        MintGreen,
        LightGreen,
        Green,
        ForestGreen,
        Lime,
        GoldenYellow,
        LightGoldenrodYellow,
        DarkKhaki,
        Khaki,
        Beige,
        Gray,
        LightGray,
        DarkGray,
        Black
    )
    val horizontalBoxBorderBrush = Brush.horizontalGradient(
        colors = listOf(
            Color.Red,
            Color.White,
            Color.Magenta,
            Color.Yellow,
            Color.Transparent,
            Color.Cyan,
            Color.Blue
        ),
        tileMode = TileMode.Mirror
    )
}