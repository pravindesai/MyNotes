package com.pravin.barcodeapp.mynotes.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val Id:Int,

    val Title:String,
    val Text:String,
    val ImagePath:String,
    val DayEpochTimeStamp: Long

)