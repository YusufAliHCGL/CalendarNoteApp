package com.example.calendarapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.calendarapp.domain.model.Note

@Database(entities = [Note::class], version = 3)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
}