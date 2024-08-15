package com.example.calendarapp.domain.use_case

import com.example.calendarapp.domain.model.Note
import com.example.calendarapp.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note = note)
    }
}