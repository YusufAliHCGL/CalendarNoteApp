package com.example.calendarapp.domain.use_case

import com.example.calendarapp.domain.model.InvalidNoteException
import com.example.calendarapp.domain.model.Note
import com.example.calendarapp.domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.dateInfo.isBlank()) {
            throw InvalidNoteException("Date shouldn't be empty")
        }
        else if (note.title.isBlank()) {
            throw InvalidNoteException("Title shouldn't be empty")
        }
        else if (note.text.isBlank()) {
            throw InvalidNoteException("Text shouldn't be empty")
        }
        else {
            repository.updateNote(note = note)
        }
    }
}