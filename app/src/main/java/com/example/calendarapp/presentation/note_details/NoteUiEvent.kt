package com.example.calendarapp.presentation.note_details

sealed class NoteUiEvent {
    class ShowSackbar(val message: String) : NoteUiEvent()
    object SaveNote : NoteUiEvent()
}