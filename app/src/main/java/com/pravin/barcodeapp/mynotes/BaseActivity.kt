package com.pravin.barcodeapp.mynotes

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.utils.SelectedDay
import com.pravin.barcodeapp.mynotes.room.NoteDB
import com.pravin.barcodeapp.mynotes.room.NoteDao
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*
import java.time.temporal.TemporalQueries.localDate
import kotlin.collections.ArrayList


open class BaseActivity: AppCompatActivity() {
    val TAG = "**";


    override fun onStart() {
        super.onStart()
        supportActionBar?.elevation = 0F
    }

    fun getToday():CalendarDay{
        val calendarDay = CalendarDay(Calendar.getInstance())
//        calendarDay.selectedLabelColor = R.color.white
        calendarDay.backgroundDrawable = resources.getDrawable(R.drawable.today_background, null)
        return calendarDay
    }

    fun getAllDaysWithNote(NOTEDAO:NoteDao):List<CalendarDay>{
        val dayList:ArrayList<CalendarDay> = ArrayList()
        val cal:Calendar = Calendar.getInstance()
        NOTEDAO.getAllDatesWithNotes().forEach {timeInMillies->
            cal.timeInMillis = timeInMillies
            val calendarDay = CalendarDay(cal)
            calendarDay.backgroundDrawable = resources.getDrawable(R.drawable.note_day_background, null)

            dayList.add(calendarDay)
            Log.e(TAG, "getAllDaysWithNote: "+calendarDay.calendar.get(Calendar.DAY_OF_MONTH)+" "+calendarDay.calendar.get(Calendar.MONTH) )
        }

        return dayList
    }

    fun getFormattedDate(cal: Calendar):String{
        //11 Oct 2021, Monday
        val dayName: String? =
            cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        val monthName: String? =
            cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())

        return      " ${cal.get(Calendar.DAY_OF_MONTH)} " +
                    monthName +
                    " ${cal.get(Calendar.YEAR)}, "+
                    dayName

    }


}