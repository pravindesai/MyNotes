package com.pravin.barcodeapp.mynotes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.CalendarView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.utils.OnPagePrepareListener
import com.pravin.barcodeapp.mynotes.room.NoteDB
import com.pravin.barcodeapp.mynotes.room.NoteDao


class NoteMainActivity : BaseActivity() {

    lateinit var materialCalendar: CalendarView
    lateinit var fab:FloatingActionButton
    lateinit var notesRv:RecyclerView
    lateinit var NOTEDAO:NoteDao

    private fun initUI() {
        materialCalendar = findViewById(R.id.mCalender)
        fab = findViewById(R.id.fab)
        notesRv = findViewById(R.id.notesRv)

        materialCalendar.setOnDayClickListener(DayClickListner())

        fab.setOnClickListener(FabClickListner(this@NoteMainActivity))

        materialCalendar.setCalendarDays(getAllDaysWithNote(NOTEDAO))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("My Notes")

        NOTEDAO = NoteDB.getInstance(this)!!.noteDao()


        initUI()

    }



    class DayClickListner:OnDayClickListener{
        override fun onDayClick(eventDay: EventDay) {

        }
    }


    class FabClickListner(val context: NoteMainActivity) :View.OnClickListener{
        override fun onClick(v: View?) {

            val takeNoteIntent = Intent(context, TakeNoteActivity::class.java)
            context.startActivity(takeNoteIntent)
        }
    }

}