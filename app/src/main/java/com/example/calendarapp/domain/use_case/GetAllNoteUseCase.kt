package com.example.calendarapp.domain.use_case

import com.example.calendarapp.domain.model.Note
import com.example.calendarapp.domain.repository.NoteRepository
import com.example.calendarapp.presentation.notes.NotesSortState
import com.example.calendarapp.presentation.notes.NotesSortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(notesSortState: NotesSortState, notesSortType: NotesSortType): Flow<List<Note>> {
        return repository.getAllNote().map { list ->
            return@map when(notesSortType) {
                NotesSortType.AscendingType -> {
                    when(notesSortState) {
                        NotesSortState.DateSort -> list.sortedBy { it.dateInMillis }
                        NotesSortState.TimeStampSort -> list.sortedBy { it.id }
                    }
                }
                NotesSortType.DescendingType -> {
                    when(notesSortState) {
                        NotesSortState.DateSort -> list.sortedByDescending { it.dateInMillis }
                        NotesSortState.TimeStampSort -> list.sortedByDescending { it.id }
                    }
                }
            }
        }
    }
}