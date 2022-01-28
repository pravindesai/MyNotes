package com.pravin.mynotes

import com.pravin.mynotes.room.Note

interface OnNoteListner {
    fun onNoteDeleteClicked(note:Note, pos:Int)
    fun onNoteClicked(note:Note, pos:Int)

}