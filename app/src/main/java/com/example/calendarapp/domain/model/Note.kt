package com.example.calendarapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.String

@Entity(tableName = "Note")
data class Note(
    @ColumnInfo(name = "background_color")
    val backgroundColor: Int,
    @ColumnInfo(name = "title_color")
    val titleColor: Int,
    @ColumnInfo(name = "text_color")
    val textColor: Int,
    @ColumnInfo(name = "border_color")
    val borderColor: Int,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "date_info")
    val dateInfo: String,
    @ColumnInfo(name = "date_in_millis")
    val dateInMillis: Long,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null
)

class InvalidNoteException(message: String) : Exception(message)