package com.pravin.barcodeapp.mynotes.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDB:RoomDatabase() {
    abstract fun noteDao():NoteDao

    companion object{
        private var DBInstance: NoteDB? = null
        private val DBNAME = "NOTEDATABASE"

        fun getInstance(context: Context): NoteDB? {
            if (DBInstance == null) {
                synchronized(NoteDB::class) {
                    DBInstance = Room.databaseBuilder(context.applicationContext,
                        NoteDB::class.java, DBNAME).allowMainThreadQueries()
                        .build()
                }
            }
            return DBInstance
        }
    }
}