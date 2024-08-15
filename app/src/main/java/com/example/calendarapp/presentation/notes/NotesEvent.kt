package com.example.calendarapp.presentation.notes

import com.example.calendarapp.domain.model.Note

sealed class NotesEvent {
    class UpdateNotesSortType(val notesSortType: NotesSortType) : NotesEvent()
    class UpdateNotesSortState(val notesSortState: NotesSortState) : NotesEvent()
    class DeleteNote(val note: Note) : NotesEvent()
    class Undo(val note: Note) : NotesEvent()
}