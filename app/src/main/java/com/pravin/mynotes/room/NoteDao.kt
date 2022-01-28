package com.pravin.mynotes.room

import androidx.room.*

@Dao
public interface NoteDao {

    @Query("select * from Note")
    fun getAllNotes():List<Note>

    @Query("select DayEpochTimeStamp from Note")
    fun getAllDatesWithNotes():List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(note: Note): Int

    @Delete
    fun deleteNote(note: Note)

    @Query("select DayEpochTimeStamp from Note where DayEpochTimeStamp>= :start and DayEpochTimeStamp<= :end")
    fun getAllDatesWithNotesFor(start:Long, end:Long):List<Long>

    @Query("select * from Note where DayEpochTimeStamp>= :start and DayEpochTimeStamp<= :end")
    fun getAllNotesFor(start:Long, end:Long):List<Note>


}