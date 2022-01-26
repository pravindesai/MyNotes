package com.pravin.barcodeapp.mynotes

import com.pravin.barcodeapp.mynotes.room.Note

interface OnNoteListner {
    fun onNoteDeleteClicked(note:Note, pos:Int)
    fun onNoteClicked(note:Note, pos:Int)

}