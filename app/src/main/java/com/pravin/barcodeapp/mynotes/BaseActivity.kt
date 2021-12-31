package com.pravin.barcodeapp.mynotes

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.utils.SelectedDay
import java.util.*

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


}