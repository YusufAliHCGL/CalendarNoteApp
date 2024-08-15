package com.example.calendarapp.presentation.notes

import com.example.calendarapp.domain.model.Note

sealed class NotesUiEvent {
    class ShowSackbar(val note: Note) : NotesUiEvent()
}