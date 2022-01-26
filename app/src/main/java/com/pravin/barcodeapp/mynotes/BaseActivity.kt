package com.pravin.barcodeapp.mynotes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarview.CalendarDay
import com.pravin.barcodeapp.mynotes.room.Note
import com.pravin.barcodeapp.mynotes.room.NoteDao
import java.util.*
import kotlin.collections.ArrayList


open class BaseActivity: AppCompatActivity() {
    val TAG = "**";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.elevation = 0F
    }

    companion object{
        var sharedNote:Note = Note(1,"","", null, Long.MIN_VALUE)
        fun getStartTimestamp(month: Int, year: Int): Long {
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar[Calendar.MONTH] = month
            calendar[Calendar.YEAR] = year
            calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            return calendar.timeInMillis
        }

        fun getEndTimestamp(month: Int, year: Int): Long {
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar[Calendar.MONTH] = month
            calendar[Calendar.YEAR] = year
            calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            calendar[Calendar.HOUR_OF_DAY] = 23
            calendar[Calendar.MINUTE] = 59
            calendar[Calendar.SECOND] = 59
            calendar[Calendar.MILLISECOND] = 0
            return calendar.timeInMillis
        }
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

    fun epochToCalender(timeInMillies:Long):Calendar{
        val cal = Calendar.getInstance()
        cal.timeInMillis = timeInMillies
        return cal
    }


}