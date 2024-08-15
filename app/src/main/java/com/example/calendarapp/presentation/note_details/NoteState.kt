package com.example.calendarapp.presentation.note_details

import androidx.compose.ui.graphics.Color
import com.example.calendarapp.presentation.util.Util

data class NoteState(
    val backgroundSelectedColor: Color = Util.colorList.random(),
    val titleSelectedColor: Color = Util.colorList.filter {
        it != backgroundSelectedColor
    }.random(),
    val textSelectedColor: Color = Util.colorList.filter {
        it != backgroundSelectedColor
    }.random(),
    val borderSelectedColor: Color = Util.colorList.filter {
        it != backgroundSelectedColor
    }.random(),
    val title: String = "",
    val text: String = "",
    val isNew: Boolean = true,
    val noteId: Int? = null,
    val dateInfo: String = "",
    val dateInMillis: Long = 0L,
    val isTitleHintShowing: Boolean = title.isEmpty(),
    val isTextHintShowing: Boolean = title.isEmpty()
)
