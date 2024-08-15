package com.example.calendarapp.presentation.notes

import com.example.calendarapp.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val notesSortType: NotesSortType = NotesSortType.AscendingType,
    val notesSortState: NotesSortState = NotesSortState.TimeStampSort
)