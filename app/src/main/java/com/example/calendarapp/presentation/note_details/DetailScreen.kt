package com.example.calendarapp.presentation.note_details

import android.icu.util.Calendar
import android.view.ViewOutlineProvider
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.calendarapp.R
import com.example.calendarapp.domain.model.Note
import com.example.calendarapp.presentation.components.CalendarElevatedCard
import com.example.calendarapp.presentation.components.ColorRow
import com.example.calendarapp.presentation.components.ElevatedActionButton
import com.example.calendarapp.presentation.components.WindowInfo
import com.example.calendarapp.presentation.components.rememberWindowInfo
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val snackbarState = remember {
        SnackbarHostState()
    }
    val noteState = detailViewModel.noteState.value

    val (isColorFilterSelected, setColorFilterState) = rememberSaveable {
        mutableStateOf(false)
    }
    val (isDialogOpen, setDialogState) = rememberSaveable {
        mutableStateOf(false)
    }
    val columnScrollState = rememberScrollState()
    val (isDatePickerShow, setDatePickerState) = rememberSaveable {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableStateOf(Calendar.getInstance())
    }
    val windowInfo = rememberWindowInfo()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarState)
        }
    ) { innerPadding ->
        Surface(
            color = if(isSystemInDarkTheme()) Color(0xFF363636) else Color(0xFFFFE9E9)
        ) {

            LaunchedEffect(key1 = true) {
                detailViewModel.eventFlow.collectLatest { event ->
                    when(event) {
                        NoteUiEvent.SaveNote -> {
                            navController.navigateUp()
                        }
                        is NoteUiEvent.ShowSackbar -> {
                            snackbarState.showSnackbar(
                                message = event.message
                            )
                        }
                    }
                }

            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(columnScrollState)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            setColorFilterState(!isColorFilterSelected)
                        },
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(CircleShape)
                            .background(if (isSystemInDarkTheme()) Color.Black else Color.White),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_filter_list_24),
                            contentDescription = "Color Filter",
                            modifier = Modifier.fillMaxSize(),
                            tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }
                }
                AnimatedVisibility(
                    visible = isColorFilterSelected,
                    enter = fadeIn() + slideInVertically() + scaleIn(),
                    exit = fadeOut() + slideOutVertically() + scaleOut()
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(10.dp))
                        ColorRow(
                            text = "Background Color",
                            selectedColor = noteState.backgroundSelectedColor
                        ) { color ->
                            detailViewModel.onEvent(
                                NoteEvent.UpdateBackgroundColor(backgroundColor = color)
                            )
                        }
                        ColorRow(
                            text = "Title Color",
                            selectedColor = noteState.titleSelectedColor
                        ) { color ->
                            detailViewModel.onEvent(
                                NoteEvent.UpdateTitleColor(titleColor = color)
                            )
                        }
                        ColorRow(
                            text = "Text Color",
                            selectedColor = noteState.textSelectedColor
                        ) { color ->
                            detailViewModel.onEvent(
                                NoteEvent.UpdateTextColor(textColor = color)
                            )
                        }
                        ColorRow(
                            text = "Border Color",
                            selectedColor = noteState.borderSelectedColor
                        ) { color ->
                            detailViewModel.onEvent(
                                NoteEvent.UpdateBorderColor(borderColor = color)
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(5.dp))
                if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))
                        TextButton(
                            onClick = {
                                setDatePickerState(!isDatePickerShow)
                            },
                            elevation = ButtonDefaults.elevatedButtonElevation(
                                if(!isDatePickerShow) 0.dp else 0.001.dp
                            )
                        ) {
                            Text(
                                text = noteState.dateInfo.ifBlank { "Selected Date" },
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Blue
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        AnimatedVisibility(
                            visible = isDatePickerShow,
                        ) {

                            AndroidView(
                                factory = { context ->
                                    DatePicker(context).apply {
                                        if (!noteState.isNew) {
                                            selectedDate.timeInMillis = noteState.dateInMillis
                                        }
                                        updateDate(
                                            selectedDate.get(Calendar.YEAR),
                                            selectedDate.get(Calendar.MONTH),
                                            selectedDate.get(Calendar.DAY_OF_MONTH)
                                        )
                                        val calendar = Calendar.getInstance()
                                        setOnDateChangedListener { _, year, month, dayOfMonth ->
                                            calendar.set(year, month, dayOfMonth)
                                            detailViewModel.onEvent(
                                                NoteEvent.UpdateDateInfo(
                                                    dateInfo = "${
                                                        "%02d".format(
                                                            dayOfMonth
                                                        )
                                                    }/${"%02d".format(month + 1)}/${year}"
                                                )
                                            )
                                            selectedDate = calendar
                                            detailViewModel.onEvent(
                                                NoteEvent.UpdateDateInMillis(
                                                    selectedDate.timeInMillis
                                                )
                                            )
                                        }
                                        this.minDate = calendar.timeInMillis
                                        this.clipToOutline = true
                                        this.outlineProvider = ViewOutlineProvider.PADDED_BOUNDS
                                        this.elevation = 6f
                                        this.setBackgroundColor(Color(0xFFAAEEEE).toArgb())
                                    }
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        CalendarElevatedCard(
                            backgroundColor = noteState.backgroundSelectedColor,
                            borderColor = noteState.borderSelectedColor,
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .padding(10.dp)
                                ) {
                                    BasicTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .onFocusChanged { focusState ->
                                                detailViewModel.onEvent(
                                                    NoteEvent.ChangeTitleFocus(
                                                        focusState
                                                    )
                                                )
                                            },
                                        value = noteState.title,
                                        onValueChange = {
                                            detailViewModel.onEvent(
                                                NoteEvent.UpdateTitle(title = it)
                                            )
                                        },
                                        textStyle = TextStyle(
                                            color = noteState.titleSelectedColor,
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.SemiBold
                                        ),
                                        minLines = 1,
                                        maxLines = 1,
                                        keyboardOptions = KeyboardOptions(
                                            capitalization = KeyboardCapitalization.Sentences
                                        )
                                    )
                                    if (noteState.isTitleHintShowing) {
                                        Text(
                                            text = "Title",
                                            color = Color.LightGray,
                                            fontSize = 30.sp
                                        )
                                    }
                                }
                                HorizontalDivider(
                                    thickness = 3.dp,
                                    color = noteState.borderSelectedColor
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(10.dp)
                                ) {
                                    BasicTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .onFocusChanged { focusState ->
                                                detailViewModel.onEvent(
                                                    NoteEvent.ChangeTextFocus(
                                                        focusState
                                                    )
                                                )
                                            },
                                        value = noteState.text,
                                        onValueChange = {
                                            detailViewModel.onEvent(
                                                NoteEvent.UpdateText(text = it)
                                            )
                                        },
                                        textStyle = TextStyle(
                                            color = noteState.textSelectedColor,
                                            fontSize = 24.sp
                                        ),
                                        minLines = 10,
                                        maxLines = 10,
                                        keyboardOptions = KeyboardOptions(
                                            capitalization = KeyboardCapitalization.Sentences
                                        )
                                    )
                                    if (noteState.isTextHintShowing) {
                                        Text(
                                            text = "Text",
                                            color = Color.LightGray,
                                            fontSize = 24.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Row {
                        Spacer(modifier = Modifier.height(15.dp))
                        CalendarElevatedCard(
                            backgroundColor = noteState.backgroundSelectedColor,
                            borderColor = noteState.borderSelectedColor,
                            modifier = Modifier
                                .weight(0.6f)
                                .fillMaxWidth()

                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .padding(10.dp)
                                ) {
                                    BasicTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .onFocusChanged { focusState ->
                                                detailViewModel.onEvent(
                                                    NoteEvent.ChangeTitleFocus(
                                                        focusState
                                                    )
                                                )
                                            },
                                        value = noteState.title,
                                        onValueChange = {
                                            detailViewModel.onEvent(
                                                NoteEvent.UpdateTitle(title = it)
                                            )
                                        },
                                        textStyle = TextStyle(
                                            color = noteState.titleSelectedColor,
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.SemiBold
                                        ),
                                        minLines = 1,
                                        maxLines = 1,
                                        keyboardOptions = KeyboardOptions(
                                            capitalization = KeyboardCapitalization.Sentences
                                        )
                                    )
                                    if (noteState.isTitleHintShowing) {
                                        Text(
                                            text = "Title",
                                            color = Color.LightGray,
                                            fontSize = 30.sp
                                        )
                                    }
                                }
                                HorizontalDivider(
                                    thickness = 3.dp,
                                    color = noteState.borderSelectedColor
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(10.dp)
                                ) {
                                    BasicTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .onFocusChanged { focusState ->
                                                detailViewModel.onEvent(
                                                    NoteEvent.ChangeTextFocus(
                                                        focusState
                                                    )
                                                )
                                            },
                                        value = noteState.text,
                                        onValueChange = {
                                            detailViewModel.onEvent(
                                                NoteEvent.UpdateText(text = it)
                                            )
                                        },
                                        textStyle = TextStyle(
                                            color = noteState.textSelectedColor,
                                            fontSize = 24.sp
                                        ),
                                        minLines = 10,
                                        keyboardOptions = KeyboardOptions(
                                            capitalization = KeyboardCapitalization.Sentences
                                        )
                                    )
                                    if (noteState.isTextHintShowing) {
                                        Text(
                                            text = "Text",
                                            color = Color.LightGray,
                                            fontSize = 24.sp
                                        )
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier.weight(0.5f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextButton(
                                onClick = {
                                    setDatePickerState(!isDatePickerShow)
                                },
                                elevation = ButtonDefaults.elevatedButtonElevation(
                                    if(!isDatePickerShow) 0.dp else 0.001.dp
                                )
                            ) {
                                Text(
                                    text = noteState.dateInfo.ifBlank { "Selected Date" },
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Blue
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            AnimatedVisibility(
                                visible = isDatePickerShow,
                            ) {
                                AndroidView(
                                    factory = { context ->
                                        DatePicker(context).apply {
                                            if (!noteState.isNew) {
                                                selectedDate.timeInMillis = noteState.dateInMillis
                                            }
                                            updateDate(
                                                selectedDate.get(Calendar.YEAR),
                                                selectedDate.get(Calendar.MONTH),
                                                selectedDate.get(Calendar.DAY_OF_MONTH)
                                            )
                                            val calendar = Calendar.getInstance()
                                            setOnDateChangedListener { _, year, month, dayOfMonth ->
                                                calendar.set(year, month, dayOfMonth)
                                                detailViewModel.onEvent(
                                                    NoteEvent.UpdateDateInfo(
                                                        dateInfo = "${
                                                            "%02d".format(
                                                                dayOfMonth
                                                            )
                                                        }/${"%02d".format(month + 1)}/${year}"
                                                    )
                                                )
                                                selectedDate = calendar
                                                detailViewModel.onEvent(
                                                    NoteEvent.UpdateDateInMillis(
                                                        selectedDate.timeInMillis
                                                    )
                                                )
                                            }
                                            this.minDate = calendar.timeInMillis
                                            this.clipToOutline = true
                                            this.outlineProvider = ViewOutlineProvider.PADDED_BOUNDS
                                            this.elevation = 6f
                                            this.setBackgroundColor(Color(0xFFAAEEEE).toArgb())
                                        }
                                    },
                                    modifier = Modifier.padding(horizontal = 5.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                if(noteState.isNew) {
                    ElevatedActionButton(
                        text = "Add Note",
                        containerColor = Color(0xFF10B0CC),
                        textColor = Color.White
                    ) {
                        val note = Note(
                            backgroundColor = noteState.backgroundSelectedColor.toArgb(),
                            titleColor = noteState.titleSelectedColor.toArgb(),
                            textColor = noteState.textSelectedColor.toArgb(),
                            borderColor = noteState.borderSelectedColor.toArgb(),
                            title = noteState.title,
                            text = noteState.text,
                            dateInfo = noteState.dateInfo,
                            dateInMillis = noteState.dateInMillis
                        )
                        detailViewModel.onEvent(NoteEvent.AddNote(note))
                    }
                } else {
                        ElevatedActionButton(
                            text = "Update Note",
                            containerColor = Color(0xFF127129),
                            textColor = Color.White
                        ) {
                            val note = Note(
                                backgroundColor = noteState.backgroundSelectedColor.toArgb(),
                                titleColor = noteState.titleSelectedColor.toArgb(),
                                textColor = noteState.textSelectedColor.toArgb(),
                                borderColor = noteState.borderSelectedColor.toArgb(),
                                title = noteState.title,
                                text = noteState.text,
                                dateInfo = noteState.dateInfo,
                                dateInMillis = noteState.dateInMillis,
                                id = noteState.noteId!!
                            )
                            detailViewModel.onEvent(NoteEvent.UpdateNote(note))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        ElevatedActionButton(
                            text = "Delete Note",
                            containerColor = Color(0xFFEE1313),
                            textColor = Color.White
                        ) {
                            setDialogState(true)
                        }

                }
                Spacer(modifier = Modifier.height(10.dp))
                if (isDialogOpen) {
                    Dialog(
                        onDismissRequest = {
                            setDialogState(false)
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
                                .padding(15.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = """
                                    Selected note will be deleted!
                                    Are you sure?
                                """.trimIndent(),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = {
                                        setDialogState(false)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Cyan
                                    )
                                ) {
                                    Text(
                                        text = "No",
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if(isSystemInDarkTheme()) Color.White else Color.Black
                                    )
                                }
                                Button(
                                    onClick = {
                                        setDialogState(false)
                                        val note = Note(
                                            backgroundColor = noteState.backgroundSelectedColor.toArgb(),
                                            titleColor = noteState.titleSelectedColor.toArgb(),
                                            textColor = noteState.textSelectedColor.toArgb(),
                                            borderColor = noteState.borderSelectedColor.toArgb(),
                                            title = noteState.title,
                                            text = noteState.text,
                                            dateInfo = noteState.dateInfo,
                                            dateInMillis = noteState.dateInMillis,
                                            id = noteState.noteId!!
                                        )
                                        detailViewModel.onEvent(NoteEvent.DeleteNote(note))
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Green
                                    )
                                ) {
                                    Text(
                                        text = "Yes",
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if(isSystemInDarkTheme()) Color.White else Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}