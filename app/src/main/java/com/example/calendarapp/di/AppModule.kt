package com.example.calendarapp.di

import android.content.Context
import androidx.room.Room
import com.example.calendarapp.data.repository.NoteRepositoryImpl
import com.example.calendarapp.data.room.NoteDatabase
import com.example.calendarapp.domain.repository.NoteRepository
import com.example.calendarapp.domain.use_case.AddNoteUseCase
import com.example.calendarapp.domain.use_case.DeleteNoteUseCase
import com.example.calendarapp.domain.use_case.GetAllNoteUseCase
import com.example.calendarapp.domain.use_case.GetNoteByIdUseCase
import com.example.calendarapp.domain.use_case.NoteUseCases
import com.example.calendarapp.domain.use_case.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = NoteDatabase::class.java,
            name = "note_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            addNoteUseCase = AddNoteUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            updateNoteUseCase = UpdateNoteUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository),
            getAllNoteUseCase = GetAllNoteUseCase(repository)
        )
    }

}