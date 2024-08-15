package com.example.calendarapp.data.repository

import com.example.calendarapp.data.room.NoteDao
import com.example.calendarapp.domain.model.Note
import com.example.calendarapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {
    override suspend fun addNote(note: Note) {
        dao.insertNote(note = note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note = note)
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note = note)
    }

    override suspend fun getNoteById(id: Int): Note {
        return dao.getNoteById(id = id)
    }

    override fun getAllNote(): Flow<List<Note>> {
        return dao.getAllNote()
    }

}