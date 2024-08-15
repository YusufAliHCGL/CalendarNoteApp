package com.example.calendarapp.domain.repository

import com.example.calendarapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun getNoteById(id: Int): Note
    fun getAllNote(): Flow<List<Note>>
}