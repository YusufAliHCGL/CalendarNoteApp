package com.example.calendarapp.presentation.note_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendarapp.domain.model.InvalidNoteException
import com.example.calendarapp.domain.model.Note
import com.example.calendarapp.domain.use_case.NoteUseCases
import com.example.calendarapp.presentation.util.Constants.NOTE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteState= mutableStateOf(NoteState())
    val noteState: State<NoteState>
        get() = _noteState

    private val _eventFlow = MutableSharedFlow<NoteUiEvent>()
    val eventFlow: SharedFlow<NoteUiEvent>
        get() = _eventFlow

    init {
        savedStateHandle.get<Int>(NOTE_ID)?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteByIdUseCase(id = noteId).apply {
                        _noteState.value = noteState.value.copy(
                            backgroundSelectedColor = Color(backgroundColor),
                            titleSelectedColor = Color(titleColor),
                            textSelectedColor = Color(textColor),
                            borderSelectedColor = Color(borderColor),
                            text = text,
                            title = title,
                            isNew = false,
                            noteId = noteId,
                            dateInfo = dateInfo,
                            dateInMillis = dateInMillis,
                            isTextHintShowing = false,
                            isTitleHintShowing = false,
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: NoteEvent) {
        when(event) {
            is NoteEvent.AddNote -> {
                addNote(event.note)
            }
            is NoteEvent.DeleteNote -> {
                deleteNote(event.note)
            }
            is NoteEvent.UpdateNote -> {
                updateNote(event.note)
            }
            is NoteEvent.UpdateText -> {
                updateText(event.text)
            }
            is NoteEvent.UpdateTitle -> {
                updateTitle(event.title)
            }
            is NoteEvent.UpdateBackgroundColor -> {
                updateBackgroundColor(event.backgroundColor)
            }
            is NoteEvent.UpdateTextColor -> {
                updateTextColor(event.textColor)
            }
            is NoteEvent.UpdateTitleColor -> {
                updateTitleColor(event.titleColor)
            }
            is NoteEvent.ChangeTextFocus -> {
                changeTextHintState(event.focusState)
            }
            is NoteEvent.ChangeTitleFocus -> {
               changeTitleHintState(event.focusState)
            }
            is NoteEvent.UpdateDateInMillis -> {
                updateDateInMillis(event.dateInMillis)
            }
            is NoteEvent.UpdateDateInfo -> {
                updateDateInfo(event.dateInfo)
            }
            is NoteEvent.UpdateBorderColor -> {
                updateBorderColor(event.borderColor)
            }
        }
    }

    private fun addNote(note: Note) {
        viewModelScope.launch {
            try {
                noteUseCases.addNoteUseCase(note = note)
                _eventFlow.emit(NoteUiEvent.SaveNote)
            } catch (e: InvalidNoteException) {
                _eventFlow.emit(NoteUiEvent.ShowSackbar(e.message ?: "Unpredictable Error"))
            }
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                noteUseCases.deleteNoteUseCase(note = note)
                _eventFlow.emit(NoteUiEvent.SaveNote)
            } catch (e: Exception)  {
                _eventFlow.emit(NoteUiEvent.ShowSackbar(e.message ?: "Unpredictable Error"))
            }
        }
    }

    private fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                noteUseCases.updateNoteUseCase(note = note)
                _eventFlow.emit(NoteUiEvent.SaveNote)
            } catch (e: InvalidNoteException) {
                _eventFlow.emit(NoteUiEvent.ShowSackbar(e.message ?: "Unpredictable Error"))
            }
        }
    }

    private fun updateTitle(title: String) {
        _noteState.value = noteState.value.copy(
            title = title
        )
    }

    private fun updateText(text: String) {
        _noteState.value = noteState.value.copy(
            text = text
        )
    }

    private fun updateBackgroundColor(backgroundColor: Color) {
        _noteState.value = noteState.value.copy(
            backgroundSelectedColor = backgroundColor
        )
    }

    private fun updateTitleColor(titleColor: Color) {
        _noteState.value = noteState.value.copy(
            titleSelectedColor = titleColor
        )
    }

    private fun updateTextColor(textColor: Color) {
        _noteState.value = noteState.value.copy(
            textSelectedColor = textColor
        )
    }

    private fun updateBorderColor(borderColor: Color) {
        _noteState.value = noteState.value.copy(
            borderSelectedColor = borderColor
        )
    }

    private fun changeTextHintState(focusState: FocusState) {
        _noteState.value = noteState.value.copy(
            isTextHintShowing = noteState.value.text.isEmpty() && !focusState.isFocused
        )
    }

    private fun changeTitleHintState(focusState: FocusState) {
        _noteState.value = noteState.value.copy(
            isTitleHintShowing = noteState.value.title.isEmpty() && !focusState.isFocused
        )
    }

    private fun updateDateInMillis(dateInMillis: Long) {
        _noteState.value = noteState.value.copy(
            dateInMillis = dateInMillis
        )
    }

    private fun updateDateInfo(dateInfo: String) {
        _noteState.value = noteState.value.copy(
            dateInfo = dateInfo
        )
    }
}