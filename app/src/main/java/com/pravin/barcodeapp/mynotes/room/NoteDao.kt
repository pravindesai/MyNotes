package com.pravin.barcodeapp.mynotes.room

import android.util.Log
import androidx.room.*
import com.applandeo.materialcalendarview.CalendarDay
import com.pravin.barcodeapp.mynotes.R
import java.time.Year
import java.util.*

@Dao
public interface NoteDao {

    @Query("select * from Note")
    fun getAllNotes():List<Note>

    @Query("select DayEpochTimeStamp from Note")
    fun getAllDatesWithNotes():List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Delete
    fun deleteNote(note: Note)



}