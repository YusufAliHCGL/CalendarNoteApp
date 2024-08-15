package com.example.calendarapp.presentation.note_details

import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import com.example.calendarapp.domain.model.Note

sealed class NoteEvent {
    class AddNote(val note: Note) : NoteEvent()
    class DeleteNote(val note: Note) : NoteEvent()
    class UpdateNote(val note: Note) : NoteEvent()
    class UpdateTitle(val title: String) : NoteEvent()
    class UpdateText(val text: String) : NoteEvent()
    class UpdateBackgroundColor(val backgroundColor: Color) : NoteEvent()
    class UpdateTitleColor(val titleColor: Color) : NoteEvent()
    class UpdateTextColor(val textColor: Color) : NoteEvent()
    class UpdateBorderColor(val borderColor: Color) : NoteEvent()
    class UpdateDateInfo(val dateInfo: String) : NoteEvent()
    class UpdateDateInMillis(val dateInMillis: Long): NoteEvent()
    class ChangeTitleFocus(val focusState: FocusState) : NoteEvent()
    class ChangeTextFocus(val focusState: FocusState) : NoteEvent()
}