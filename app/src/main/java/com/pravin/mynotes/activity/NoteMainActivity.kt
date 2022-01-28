package com.pravin.mynotes.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.pravin.mynotes.adapter.NotesAdapter
import com.pravin.mynotes.listeners.OnNoteListner
import com.pravin.mynotes.R
import com.pravin.mynotes.room.Note
import com.pravin.mynotes.room.NoteDB
import com.pravin.mynotes.room.NoteDao
import kotlin.collections.ArrayList


class NoteMainActivity : BaseActivity() {

    lateinit var materialCalendar: CalendarView
    lateinit var fab:FloatingActionButton
    lateinit var notesRv:RecyclerView
    lateinit var NOTEDAO:NoteDao

    lateinit var notesList:List<Note>
    lateinit var notesAdapter: NotesAdapter

    public var selectedDay:Calendar = Calendar.getInstance()

    lateinit var mOnNoteClickListner: OnNoteClickListner

    private fun initUI() {
        materialCalendar = findViewById(R.id.mCalender)
        fab = findViewById(R.id.fab)
        notesRv = findViewById(R.id.notesRv)

        materialCalendar.setOnDayClickListener(DayClickListner(this@NoteMainActivity))
        materialCalendar.setOnForwardPageChangeListener(CalenderPageForward(this@NoteMainActivity))
        materialCalendar.setOnPreviousPageChangeListener(CalenderPagePrevious(this@NoteMainActivity))
        mOnNoteClickListner = OnNoteClickListner()

        fab.setOnClickListener(FabClickListner(this@NoteMainActivity))

//      materialCalendar.setCalendarDays(getAllDaysWithNote(NOTEDAO))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("My Notes")

        NOTEDAO = NoteDB.getInstance(this)!!.noteDao()

        initUI()

        Log.e(TAG, "seleted dates: "+materialCalendar.selectedDates.size )
        Log.e(TAG, "total dates: "+NOTEDAO.getAllDatesWithNotes().size )
        Log.e(TAG, "date for month dates: "+ NOTEDAO.getAllNotesFor(
            getStartTimestamp(selectedDay[Calendar.MONTH], selectedDay[Calendar.YEAR] ),
            getEndTimestamp(selectedDay[Calendar.MONTH], selectedDay[Calendar.YEAR] )
        )
        )
    }


    override fun onStart() {
        super.onStart()

        val linearLayoutManager = LinearLayoutManager(this)
        notesRv.layoutManager = linearLayoutManager

        notesList = ArrayList(NOTEDAO.getAllNotesFor(
            getStartTimestamp(selectedDay[Calendar.MONTH], selectedDay[Calendar.YEAR] ),
            getEndTimestamp(selectedDay[Calendar.MONTH], selectedDay[Calendar.YEAR] )
        ))

            notesAdapter = NotesAdapter(this, notesList, mOnNoteClickListner)
            notesRv.adapter = notesAdapter

    }


    class DayClickListner(val context: NoteMainActivity):OnDayClickListener{
        override fun onDayClick(eventDay: EventDay) {
            context.selectedDay = eventDay.calendar
        }
    }

    class CalenderPageForward(val noteMainActivity: NoteMainActivity) : OnCalendarPageChangeListener {
        override fun onChange() {
            noteMainActivity.updateRv(+1)
        }

    }

    class CalenderPagePrevious(val noteMainActivity: NoteMainActivity) : OnCalendarPageChangeListener {
        override fun onChange() {
            noteMainActivity.updateRv(-1)
        }

    }

    class FabClickListner(val context: NoteMainActivity) :View.OnClickListener{
        override fun onClick(v: View?) {

            val takeNoteIntent = Intent(context, TakeNoteActivity::class.java)
            takeNoteIntent.putExtra("DATE", context.selectedDay.timeInMillis)
            takeNoteIntent.putExtra("MODE", TakeNoteActivity.NEW_NOTE_MODE)
            context.startActivity(takeNoteIntent)
        }
    }

    inner class OnNoteClickListner: OnNoteListner {
        override fun onNoteDeleteClicked(note: Note, pos:Int) {
            NOTEDAO.deleteNote(note)

            updateRv(0)
        }

        override fun onNoteClicked(note: Note, pos: Int) {
            val takeNoteIntent = Intent(this@NoteMainActivity, TakeNoteActivity::class.java)
            takeNoteIntent.putExtra("DATE", selectedDay.timeInMillis)
            takeNoteIntent.putExtra("MODE", TakeNoteActivity.UPDATE_NOTE_MODE)
            sharedNote = note
            startActivity(takeNoteIntent)
        }

    }

    fun updateRv(monthCount:Int){
        var selectedDay:Calendar = selectedDay
        selectedDay[Calendar.MONTH] = selectedDay[Calendar.MONTH]+monthCount

        var list = ArrayList(NOTEDAO.getAllNotesFor(
            getStartTimestamp(selectedDay[Calendar.MONTH], selectedDay[Calendar.YEAR] ),
            getEndTimestamp(selectedDay[Calendar.MONTH], selectedDay[Calendar.YEAR] )
        ))

        notesAdapter = NotesAdapter(this@NoteMainActivity, list, mOnNoteClickListner)
        notesRv.adapter = notesAdapter
    }

}




