package com.example.calendarapp.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendarapp.domain.model.Note
import com.example.calendarapp.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _notesState = mutableStateOf(NotesState())
    val notesState: State<NotesState>
        get() = _notesState

    private val _eventFlow = MutableSharedFlow<NotesUiEvent>()
    val eventFlow: SharedFlow<NotesUiEvent>
        get() = _eventFlow

    init {
        getAll(notesSortType = _notesState.value.notesSortType, notesSortState = _notesState.value.notesSortState)
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.UpdateNotesSortType -> {
                _notesState.value = notesState.value.copy(
                    notesSortType = event.notesSortType
                )
                getAll(notesSortType = _notesState.value.notesSortType, notesSortState = _notesState.value.notesSortState)
            }
            is NotesEvent.UpdateNotesSortState -> {
                _notesState.value = notesState.value.copy(
                    notesSortState = event.notesSortState
                )
                getAll(notesSortType = _notesState.value.notesSortType, notesSortState = _notesState.value.notesSortState)
            }
            is NotesEvent.DeleteNote -> {
                deleteNote(event.note)
            }
            is NotesEvent.Undo -> {
                undoNote(event.note)
            }
        }
    }

    private fun getAll(notesSortState: NotesSortState, notesSortType: NotesSortType) {
        noteUseCases.getAllNoteUseCase(notesSortState, notesSortType).onEach {
            _notesState.value = notesState.value.copy(
                notes = it
            )
        }.launchIn(viewModelScope)
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteUseCases.deleteNoteUseCase(note = note)
            _eventFlow.emit(NotesUiEvent.ShowSackbar(note = note))
        }
    }

    private fun undoNote(note: Note) {
        viewModelScope.launch {
            try {
                noteUseCases.addNoteUseCase(note = note)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

}