package com.example.calendarapp.domain.use_case

data class NoteUseCases(
    val addNoteUseCase: AddNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val getAllNoteUseCase: GetAllNoteUseCase
)
