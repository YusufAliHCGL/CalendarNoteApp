package com.example.calendarapp.presentation.notes

sealed class NotesSortState {
    object DateSort: NotesSortState()
    object TimeStampSort: NotesSortState()
}