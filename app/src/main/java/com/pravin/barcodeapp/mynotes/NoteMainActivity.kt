package com.pravin.barcodeapp.mynotes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.pravin.barcodeapp.mynotes.room.NoteDB
import com.pravin.barcodeapp.mynotes.room.NoteDao


class NoteMainActivity : BaseActivity() {

    lateinit var materialCalendar: CalendarView
    lateinit var fab:FloatingActionButton
    lateinit var notesRv:RecyclerView
    lateinit var NOTEDAO:NoteDao

    public var selectedDay:Calendar = Calendar.getInstance()

    private fun initUI() {
        materialCalendar = findViewById(R.id.mCalender)
        fab = findViewById(R.id.fab)
        notesRv = findViewById(R.id.notesRv)

        materialCalendar.setOnDayClickListener(DayClickListner(this@NoteMainActivity))
        materialCalendar.setOnForwardPageChangeListener(CalenderPageForward(this@NoteMainActivity))
        materialCalendar.setOnPreviousPageChangeListener(CalenderPagePrevious(this@NoteMainActivity))

        fab.setOnClickListener(FabClickListner(this@NoteMainActivity))

        materialCalendar.setCalendarDays(getAllDaysWithNote(NOTEDAO))


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("My Notes")

        NOTEDAO = NoteDB.getInstance(this)!!.noteDao()

        initUI()

        Log.e(TAG, "seleted dates: "+materialCalendar.selectedDates.size )
        Log.e(TAG, "total dates: "+NOTEDAO.getAllDatesWithNotes().size )
    }



    class DayClickListner(val context: NoteMainActivity):OnDayClickListener{
        override fun onDayClick(eventDay: EventDay) {
            context.selectedDay = eventDay.calendar
        }
    }

    class CalenderPageForward(noteMainActivity: NoteMainActivity) : OnCalendarPageChangeListener {
        override fun onChange() {
            TODO("Not yet implemented")
        }

    }

    class CalenderPagePrevious(noteMainActivity: NoteMainActivity) : OnCalendarPageChangeListener {
        override fun onChange() {
            TODO("Not yet implemented")
        }

    }

    class FabClickListner(val context: NoteMainActivity) :View.OnClickListener{
        override fun onClick(v: View?) {

            val takeNoteIntent = Intent(context, TakeNoteActivity::class.java)
            takeNoteIntent.putExtra("DATE", context.selectedDay.timeInMillis)
            takeNoteIntent.putExtra("MODE",TakeNoteActivity.NEW_NOTE_MODE)
            context.startActivity(takeNoteIntent)
        }
    }

}




