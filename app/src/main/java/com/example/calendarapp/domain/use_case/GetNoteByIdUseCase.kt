package com.example.calendarapp.domain.use_case

import com.example.calendarapp.domain.model.Note
import com.example.calendarapp.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note {
        return repository.getNoteById(id = id)
    }
}