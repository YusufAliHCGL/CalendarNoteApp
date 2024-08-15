package com.example.calendarapp.presentation.notes

sealed class NotesSortType {
    object AscendingType: NotesSortType()
    object DescendingType: NotesSortType()
}