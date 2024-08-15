package com.example.calendarapp.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.calendarapp.R
import com.example.calendarapp.presentation.components.CalendarElevatedCard
import com.example.calendarapp.presentation.components.SortButton
import com.example.calendarapp.presentation.components.WindowInfo
import com.example.calendarapp.presentation.components.rememberWindowInfo
import com.example.calendarapp.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val notesState = homeViewModel.notesState.value
    val (isSortOptionsOpen, setSortOptionState) = remember {
        mutableStateOf(false)
    }
    val snackbarState = remember {
        SnackbarHostState()
    }
    val windowInfo = rememberWindowInfo()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(route = Screen.DetailScreen.route+"/${-1}")
                },
                containerColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                shape = RoundedCornerShape(15.dp),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 7.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note",
                    tint = if (isSystemInDarkTheme()) Color.Black else Color.White
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState
            )
        }
    ) { innerPadding ->
        LaunchedEffect(key1 = true) {
            homeViewModel.eventFlow.collectLatest { event ->
                when(event) {
                    is NotesUiEvent.ShowSackbar -> {
                        val result = snackbarState.showSnackbar(
                            message = "Note Deleted!",
                            actionLabel = "Undo",
                            duration = SnackbarDuration.Short
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            homeViewModel.onEvent(NotesEvent.Undo(event.note))
                        }
                    }
                }
            }
        }
        Surface(
            color = if(isSystemInDarkTheme()) Color(0xFF363636) else Color(0xFFFFE9E9)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = "Calendar Notes",
                        style = TextStyle(
                            fontSize = 36.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily.SansSerif,
                            color = Color(0xFFFF0000),
                        )
                    )
                    IconButton(
                        onClick = {
                            setSortOptionState(!isSortOptionsOpen)
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                if (isSystemInDarkTheme()) Color.Black else Color.White
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_sort_24),
                            contentDescription = "Sort Icon",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                AnimatedVisibility(
                    visible = isSortOptionsOpen,
                    enter = slideInHorizontally(),
                    exit = slideOutHorizontally()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            SortButton(
                                text = "Addition Time",
                                isSelected = notesState.notesSortState is NotesSortState.TimeStampSort
                            ) {
                                homeViewModel.onEvent(
                                    NotesEvent.UpdateNotesSortState(NotesSortState.TimeStampSort)
                                )
                            }
                            SortButton(
                                text = "Date",
                                isSelected = notesState.notesSortState is NotesSortState.DateSort
                            ) {
                                homeViewModel.onEvent(
                                    NotesEvent.UpdateNotesSortState(NotesSortState.DateSort)
                                )
                            }
                        }
                        Row {
                            SortButton(
                                text = "Ascending",
                                isSelected = notesState.notesSortType is NotesSortType.AscendingType
                            ) {
                                homeViewModel.onEvent(
                                    NotesEvent.UpdateNotesSortType(NotesSortType.AscendingType)
                                )
                            }
                            SortButton(
                                text = "Descending",
                                isSelected = notesState.notesSortType is NotesSortType.DescendingType
                            ) {
                                homeViewModel.onEvent(
                                    NotesEvent.UpdateNotesSortType(NotesSortType.DescendingType)
                                )
                            }
                        }
                    }
                }
                if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(notesState.notes) { note ->
                            CalendarElevatedCard(
                                backgroundColor = Color(note.backgroundColor),
                                borderColor = Color(note.borderColor),
                                content = {
                                    Column(
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 10.dp, horizontal = 4.dp),
                                            text = note.dateInfo,
                                            color = Color(note.titleColor),
                                            fontSize = 32.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            minLines = 1,
                                            maxLines = 1
                                        )
                                        HorizontalDivider(
                                            thickness = 3.dp,
                                            color = Color(note.borderColor)
                                        )
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 10.dp, horizontal = 4.dp),
                                            text = note.title,
                                            color = Color(note.titleColor),
                                            fontSize = 32.sp,
                                            minLines = 1,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        HorizontalDivider(
                                            thickness = 3.dp,
                                            color = Color(note.borderColor)
                                        )
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 10.dp, horizontal = 3.dp),
                                            text = note.text,
                                            color = Color(note.textColor),
                                            fontSize = 28.sp,
                                            minLines = 10,
                                            maxLines = 10,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        IconButton(
                                            onClick = {
                                                homeViewModel.onEvent(NotesEvent.DeleteNote(note = note))
                                            },
                                            modifier = Modifier
                                                .padding(2.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete Note",
                                                tint = Color.Red,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(4.dp)
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier.clickable {
                                    navController.navigate(Screen.DetailScreen.route+"/${note.id}")
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                } else {
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(notesState.notes) { note ->
                            CalendarElevatedCard(
                                backgroundColor = Color(note.backgroundColor),
                                borderColor = Color(note.borderColor),
                                content = {
                                    Column(
                                        horizontalAlignment = Alignment.End,
                                        modifier = modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 10.dp, horizontal = 4.dp),
                                            text = note.dateInfo,
                                            color = Color(note.titleColor),
                                            fontSize = 32.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            minLines = 1,
                                            maxLines = 1
                                        )
                                        HorizontalDivider(
                                            thickness = 3.dp,
                                            color = Color(note.borderColor)
                                        )
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 10.dp, horizontal = 4.dp),
                                            text = note.title,
                                            color = Color(note.titleColor),
                                            fontSize = 32.sp,
                                            minLines = 1,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        HorizontalDivider(
                                            thickness = 3.dp,
                                            color = Color(note.borderColor)
                                        )
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 10.dp, horizontal = 3.dp),
                                            text = note.text,
                                            color = Color(note.textColor),
                                            fontSize = 28.sp,
                                            minLines = 10,
                                            maxLines = 10,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        IconButton(
                                            onClick = {
                                                homeViewModel.onEvent(NotesEvent.DeleteNote(note = note))
                                            },
                                            modifier = Modifier
                                                .padding(2.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete Note",
                                                tint = Color.Red,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(4.dp)
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                    navController.navigate(Screen.DetailScreen.route+"/${note.id}")
                                }
                            )
                        }
                    }
                }





            }
        }
    }
}
